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
package org.eclipse.che.ide.ext.java.testing.testng.client;

import com.google.gwt.resources.client.ClientBundle;
import org.vectomatic.dom.svg.ui.SVGResource;
/**
 *
 * @author Mirage Abeysekara
 */
public interface TestNGResources extends ClientBundle {

    @Source("org/eclipse/che/ide/ext/java/testing/core/client/svg/test.svg")
    SVGResource testIcon();

    @Source("org/eclipse/che/ide/ext/java/testing/core/client/svg/test_all.svg")
    SVGResource testAllIcon();

}
