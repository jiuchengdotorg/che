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
package org.eclipse.che.ide.command.explorer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.che.commons.annotation.Nullable;
import org.eclipse.che.ide.api.command.CommandGoal;
import org.eclipse.che.ide.api.command.ContextualCommand;
import org.eclipse.che.ide.api.data.tree.Node;
import org.eclipse.che.ide.api.parts.base.BaseView;
import org.eclipse.che.ide.command.CommandLocalizationConstants;
import org.eclipse.che.ide.command.node.CommandFileNode;
import org.eclipse.che.ide.command.node.CommandGoalNode;
import org.eclipse.che.ide.command.node.NodeFactory;
import org.eclipse.che.ide.ui.smartTree.NodeLoader;
import org.eclipse.che.ide.ui.smartTree.NodeStorage;
import org.eclipse.che.ide.ui.smartTree.Tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.eclipse.che.ide.ui.smartTree.SelectionModel.Mode.SINGLE;

/**
 * Implementation of {@link CommandsExplorerView}.
 *
 * @author Artem Zatsarynnyi
 */
@Singleton
public class CommandsExplorerViewImpl extends BaseView<CommandsExplorerView.ActionDelegate> implements CommandsExplorerView {

    private static final CommandsExplorerViewImplUiBinder UI_BINDER = GWT.create(CommandsExplorerViewImplUiBinder.class);

    private final CommandsTreeRenderer                    treeRenderer;
    private final NodeFactory                             nodeFactory;
    private final Map<ContextualCommand, CommandFileNode> commandNodes;

    @UiField(provided = true)
    Tree tree;

    @Inject
    public CommandsExplorerViewImpl(org.eclipse.che.ide.Resources coreResources,
                                    CommandLocalizationConstants localizationConstants,
                                    CommandsExplorerResources resources,
                                    NodeFactory nodeFactory) {
        super(coreResources);

        this.nodeFactory = nodeFactory;
        commandNodes = new HashMap<>();

        resources.commandsExplorerCss().ensureInjected();

        setTitle(localizationConstants.explorerViewTitle());

        tree = new Tree(new NodeStorage(), new NodeLoader());

        treeRenderer = new CommandsTreeRenderer(tree.getTreeStyles(), resources, delegate);

        tree.setPresentationRenderer(treeRenderer);
        tree.getSelectionModel().setSelectionMode(SINGLE);

        tree.getSelectionModel().addSelectionHandler(new SelectionHandler<Node>() {
            @Override
            public void onSelection(SelectionEvent<Node> event) {
                for (Node node : tree.getNodeStorage().getAll()) {
                    final Element nodeContainerElement = tree.getNodeDescriptor(node).getNodeContainerElement();

                    if (nodeContainerElement != null) {
                        nodeContainerElement.removeAttribute("selected");
                    }
                }

                tree.getNodeDescriptor(event.getSelectedItem()).getNodeContainerElement().setAttribute("selected", "selected");
            }
        });

        setContentWidget(UI_BINDER.createAndBindUi(this));
    }

    @Override
    public void setCommands(Map<CommandGoal, List<ContextualCommand>> commands) {
        treeRenderer.setDelegate(delegate);

        renderCommands(commands);
    }

    private void renderCommands(Map<CommandGoal, List<ContextualCommand>> commands) {
        commandNodes.clear();
        tree.getNodeStorage().clear();

        for (Map.Entry<CommandGoal, List<ContextualCommand>> entry : commands.entrySet()) {
            List<CommandFileNode> commandNodes = new ArrayList<>(entry.getValue().size());
            for (ContextualCommand command : entry.getValue()) {
                final CommandFileNode commandFileNode = nodeFactory.newCommandFileNode(command);
                commandNodes.add(commandFileNode);

                this.commandNodes.put(command, commandFileNode);
            }

            final CommandGoalNode commandGoalNode = nodeFactory.newCommandGoalNode(entry.getKey(), commandNodes);
            tree.getNodeStorage().add(commandGoalNode);
        }

        tree.expandAll();
    }

    @Nullable
    @Override
    public CommandGoal getSelectedCommandGoal() {
        final List<Node> selectedNodes = tree.getSelectionModel().getSelectedNodes();

        if (!selectedNodes.isEmpty()) {
            final Node selectedNode = selectedNodes.get(0);

            if (selectedNode instanceof CommandGoalNode) {
                return ((CommandGoalNode)selectedNode).getData();
            }
        }

        return null;
    }

    @Override
    public void selectCommand(ContextualCommand command) {
        tree.getSelectionModel().setSelection(Collections.<Node>singletonList(commandNodes.get(command)));
    }

    interface CommandsExplorerViewImplUiBinder extends UiBinder<Widget, CommandsExplorerViewImpl> {
    }
}
