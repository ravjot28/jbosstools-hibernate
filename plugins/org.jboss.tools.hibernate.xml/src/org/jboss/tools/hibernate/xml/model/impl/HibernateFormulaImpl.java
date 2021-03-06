/*******************************************************************************
 * Copyright (c) 2007 Exadel, Inc. and Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Exadel, Inc. and Red Hat, Inc. - initial API and implementation
 ******************************************************************************/ 
package org.jboss.tools.hibernate.xml.model.impl;

import org.jboss.tools.common.model.impl.OrderedObjectImpl;

public class HibernateFormulaImpl extends OrderedObjectImpl {
	private static final long serialVersionUID = 1L;

	public String name() {
		return "formula"; //$NON-NLS-1$
	}
	
	public String getPathPart() {
		return "" + ("" + getAttributeValue("value")).hashCode(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
}
