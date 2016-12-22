/*******************************************************************************
 * Copyright (c) 2012-2016 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/

package org.eclipse.che.ide.command.type;

import com.google.inject.Inject;

import org.eclipse.che.api.promises.client.Promise;
import org.eclipse.che.api.promises.client.PromiseProvider;
import org.eclipse.che.api.promises.client.js.Executor;
import org.eclipse.che.api.promises.client.js.JsPromiseError;
import org.eclipse.che.api.promises.client.js.RejectFunction;
import org.eclipse.che.api.promises.client.js.ResolveFunction;
import org.eclipse.che.ide.api.command.CommandType;
import org.eclipse.che.ide.api.command.CommandTypeRegistry;
import org.eclipse.che.ide.command.CommandLocalizationConstants;

import java.util.List;

/**
 * Provides a simple mechanism for the user to choose a {@link CommandType}.
 *
 * @author Artem Zatsarynnyi
 * @see #show(int, int)
 */
public class CommandTypeChooser implements CommandTypeChooserView.ActionDelegate {

    private final CommandTypeChooserView       view;
    private final CommandTypeRegistry          commandTypeRegistry;
    private final PromiseProvider              promiseProvider;
    private final CommandLocalizationConstants localizationConstants;

    private ResolveFunction<CommandType> resolveFunction;
    private RejectFunction               rejectFunction;

    @Inject
    public CommandTypeChooser(CommandTypeChooserView view,
                              CommandTypeRegistry commandTypeRegistry,
                              PromiseProvider promiseProvider,
                              CommandLocalizationConstants localizationConstants) {
        this.view = view;
        this.commandTypeRegistry = commandTypeRegistry;
        this.promiseProvider = promiseProvider;
        this.localizationConstants = localizationConstants;

        view.setDelegate(this);
    }

    /**
     * Pops up a machine chooser dialog.
     *
     * @return promise that will be resolved with a chosen {@link CommandType}
     * or rejected in case machine selection has been cancelled
     */
    public Promise<CommandType> show(int left, int top) {
        final List<CommandType> commandTypes = commandTypeRegistry.getCommandTypes();

        if (commandTypes.size() == 1) {
            return promiseProvider.resolve(commandTypes.get(0));
        }

        view.setTypes(commandTypes);

        view.show(left, top);

        return promiseProvider.create(Executor.create(new Executor.ExecutorBody<CommandType>() {
            @Override
            public void apply(ResolveFunction<CommandType> resolve, RejectFunction reject) {
                resolveFunction = resolve;
                rejectFunction = reject;
            }
        }));
    }

    @Override
    public void onSelected(CommandType commandType) {
        view.close();

        resolveFunction.apply(commandType);
    }

    @Override
    public void onCanceled() {
        rejectFunction.apply(JsPromiseError.create(localizationConstants.typeChooserMessageCanceled()));
    }
}
