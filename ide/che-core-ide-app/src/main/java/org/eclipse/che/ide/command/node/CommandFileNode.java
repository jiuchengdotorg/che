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
package org.eclipse.che.ide.command.node;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import org.eclipse.che.api.promises.client.Promise;
import org.eclipse.che.ide.api.command.CommandTypeRegistry;
import org.eclipse.che.ide.api.command.ContextualCommand;
import org.eclipse.che.ide.api.data.tree.HasAction;
import org.eclipse.che.ide.api.editor.EditorAgent;
import org.eclipse.che.ide.api.icon.IconRegistry;
import org.eclipse.che.ide.api.resources.VirtualFile;
import org.eclipse.che.ide.resource.Path;
import org.eclipse.che.ide.ui.smartTree.presentation.NodePresentation;

/**
 * Extension of {@link AbstractCommandNode} that also acts as a {@link VirtualFile} for using it in editor.
 *
 * @author Artem Zatsarynnyi
 */
public class CommandFileNode extends AbstractCommandNode implements HasAction, VirtualFile {

    /** Extension for the file type that represents a command. */
    public static final String FILE_TYPE_EXT = "che_command_internal";

    private final EditorAgent editorAgent;

    @Inject
    public CommandFileNode(@Assisted ContextualCommand data,
                           CommandTypeRegistry commandTypeRegistry,
                           IconRegistry iconRegistry,
                           EditorAgent editorAgent) {
        super(data, null, commandTypeRegistry, iconRegistry);

        this.editorAgent = editorAgent;
    }

    @Override
    public void updatePresentation(NodePresentation presentation) {
        presentation.setPresentableText(getDisplayName());
    }

    @Override
    public void actionPerformed() {
        editorAgent.openEditor(this);
    }

    @Override
    public String getPath() {
        return "commands/" + getData().getType() + "/" + getData().getName();
    }

    @Override
    public Path getLocation() {
        return Path.valueOf(getPath());
    }

    @Override
    public String getName() {
        return getData().getName() + "." + FILE_TYPE_EXT;
    }

    @Override
    public String getDisplayName() {
        return getData().getName();
    }

    @Override
    public String getMediaType() {
        return null;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public String getContentUrl() {
        return null;
    }

    @Override
    public Promise<String> getContent() {
        return null;
    }

    @Override
    public Promise<Void> updateContent(String content) {
        return null;
    }
}