/*******************************************************************************
 * Copyright (c) 2009, Elias Volanakis and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Elias Volanakis - initial API and implementation
 *******************************************************************************/
package de.volanakis.ribbonide.internal.presentation;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.internal.presentations.PaneFolder;
import org.eclipse.ui.internal.presentations.defaultpresentation.DefaultTabFolder;
import org.eclipse.ui.internal.presentations.defaultpresentation.DefaultTabItem;
import org.eclipse.ui.internal.presentations.util.AbstractTabFolder;
import org.eclipse.ui.internal.presentations.util.AbstractTabItem;
import org.eclipse.ui.internal.presentations.util.PartInfo;

import de.volanakis.ribbonide.internal.Activator;
import de.volanakis.ribbonide.internal.SharedColors;

/**
 * @see DefaultTabFolder
 * @see PaneFolder
 */
public class PresentationTabFolder extends AbstractTabFolder implements
		IThemeCallback {

	private final CTabFolder tabFolder;
	private final ViewForm viewForm;

	private DefaultTabFolderColors[] activeColors;
	private DefaultTabFolderColors[] inactiveColors;
	private boolean isActive;

	public PresentationTabFolder(Composite parent) {
		tabFolder = new CTabFolder(parent, SWT.BORDER);
		tabFolder.setBackground(Activator
				.getSharedColor(SharedColors.WINDOW_BG));
		tabFolder.setUnselectedCloseVisible(false);

		viewForm = new ViewForm(tabFolder, SWT.NO_BACKGROUND);

		DefaultTabFolderColors defaultColors = new DefaultTabFolderColors();
		activeColors = new DefaultTabFolderColors[] { defaultColors,
				defaultColors, defaultColors };
		inactiveColors = new DefaultTabFolderColors[] { defaultColors,
				defaultColors, defaultColors };
	}

	@Override
	public AbstractTabItem add(int index, int flags) {
		DefaultTabItem result = new DefaultTabItem(tabFolder, index, flags);
		result.getWidget().setData(result);
		return result;
	}

	@Override
	public Point computeSize(int widthHint, int heightHint) {
		return computeMinimumSize();
	}

	@Override
	public void enablePaneMenu(boolean enabled) {
		// unused
	}

	@Override
	public Composite getContentParent() {
		return viewForm;
	}

	@Override
	public Control getControl() {
		return tabFolder;
	}

	@Override
	public AbstractTabItem[] getItems() {
		AbstractTabItem[] result = new AbstractTabItem[tabFolder.getItemCount()];
		for (int i = 0; i < result.length; i++) {
			CTabItem item = tabFolder.getItem(i);
			result[i] = (AbstractTabItem) item.getData();
		}
		return result;
	}

	@Override
	public AbstractTabItem getSelection() {
		CTabItem tabItem = tabFolder.getSelection();
		return (DefaultTabItem) tabItem.getData();
	}

	@Override
	public Rectangle getTabArea() {
		// TODO Auto-generated method stub
		return new Rectangle(0, 0, 10, 10);
	}

	@Override
	public Composite getToolbarParent() {
		return tabFolder;
	}

	@Override
	public void setContent(Control newContent) {
		viewForm.setContent(newContent);
	}

	@Override
	public void setSelectedInfo(PartInfo info) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setSelection(AbstractTabItem toSelect) {
		AbstractTabItem[] items = getItems();
		for (int index = 0; index < items.length; index++) {
			if (items[index] == toSelect) {
				tabFolder.setSelection(index);
				break;
			}
		}
	}

	// update active / inactive colors
	// ////////////////////////////////

	@Override
	public void setActive(int activeState) {
		super.setActive(activeState);
		updateColors();
	}

	@Override
	public void shellActive(boolean isActive) {
		this.isActive = isActive;
		super.shellActive(isActive);
		updateColors();
	}

	// IThemeCallback methods
	// ///////////////////////

	// from DefaultTabFolder
	public void setColors(DefaultTabFolderColors colors, int activationState) {
		setColors(colors, activationState, true);
		setColors(colors, activationState, false);
	}

	// from DefaultTabFolder
	public void setColors(DefaultTabFolderColors colors, int activationState,
			boolean shellActivationState) {
		Assert.isTrue(activationState < activeColors.length);

		if (shellActivationState) {
			activeColors[activationState] = colors;
		} else {
			inactiveColors[activationState] = colors;
		}

		if (activationState == getActive() && isActive == shellActivationState) {
			updateColors();
		}
	}

	public void setFont(Font font) {
		// unused
	}

	// helping methods
	// ////////////////

	private Point computeMinimumSize() {
		Rectangle trim = tabFolder.computeTrim(0, 0, 0, 0);
		// 100 extra space for min, max buttons
		Point result = new Point(trim.width + 100, trim.height);
		return result;
	}

	private void updateColors() {
		int actState = getActive();
		DefaultTabFolderColors currentColors = isActive ? activeColors[actState]
				: inactiveColors[actState];

		tabFolder.setSelectionForeground(currentColors.foreground);
		tabFolder.setSelectionBackground(currentColors.background,
				currentColors.percentages, currentColors.vertical);
	}

}
