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

package org.eclipse.che.ide.command;

import com.google.gwt.i18n.client.Messages;

/**
 * I18n messages for the command-related UI.
 *
 * @author Artem Zatsarynnyi
 */
public interface CommandLocalizationConstants extends Messages {

    /* Command Execution */
    @Key("exec.action.title")
    String execActionTitle();

    /* Command Goals */
    @Key("goal.build.id")
    String goalBuildId();

    @Key("goal.build.name")
    String goalBuildName();

    @Key("goal.common.id")
    String goalCommonId();

    @Key("goal.common.name")
    String goalCommonName();

    @Key("goal.deploy.id")
    String goalDeployId();

    @Key("goal.deploy.name")
    String goalDeployName();

    @Key("goal.run.id")
    String goalRunId();

    @Key("goal.run.name")
    String goalRunName();

    @Key("goal.test.id")
    String goalTestId();

    @Key("goal.test.name")
    String goalTestName();

    @Key("goal.message.already_registered")
    String goalMessageAlreadyRegistered(String id);

    /* Commands Explorer */
    @Key("explorer.part.title")
    String explorerPartTitle();

    @Key("explorer.part.tooltip")
    String explorerPartTooltip();

    @Key("explorer.view.title")
    String explorerViewTitle();

    @Key("explorer.message.unable_create")
    String explorerMessageUnableCreate();

    @Key("explorer.message.unable_duplicate")
    String explorerMessageUnableDuplicate();

    @Key("explorer.message.unable_remove")
    String explorerMessageUnableRemove();

    /* Command Palette */
    @Key("palette.action.title")
    String paletteActionTitle();

    @Key("palette.action.description")
    String paletteActionDescription();

    @Key("palette.view.title")
    String paletteViewTitle();

    @Key("palette.filter.tooltip")
    String paletteFilterTooltip();

    @Key("palette.message.no_machine")
    String paletteMessageNoMachine();

    /* Command Type */
    @Key("type.registry.message.already_registered")
    String typeRegistryMessageAlreadyRegistered(String id);

    @Key("type.chooser.message.canceled")
    String typeChooserMessageCanceled();

    /* Command Producer */
    @Key("producer.action.title")
    String producerActionTitle();

    @Key("producer.action.description")
    String producerActionDescription();
}
