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
import org.eclipse.swt.custom.CTabFolder2Listener;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
import org.eclipse.ui.internal.presentations.util.TabFolderEvent;
import org.eclipse.ui.presentations.IStackPresentationSite;

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
	// private final ProxyControl toolbarProxy;
	// private final ProxyControl contentProxy;

	private DefaultTabFolderColors[] activeColors;
	private DefaultTabFolderColors[] inactiveColors;
	private boolean isActive;

	public PresentationTabFolder(Composite parent, int style,
			IStackPresentationSite site) {
		tabFolder = new CTabFolder(parent, style);
		tabFolder.setBackground(Activator
				.getSharedColor(SharedColors.WINDOW_BG));
		tabFolder.setUnselectedCloseVisible(false);
		tabFolder.setMinimizeVisible(allowMin(site));
		tabFolder.setMaximizeVisible(allowMax(site));
		ExpandListener listener = new ExpandListener();
		tabFolder.addCTabFolder2Listener(listener);
		tabFolder.addSelectionListener(listener);
		attachListeners(tabFolder, false);

		viewForm = new ViewForm(tabFolder, SWT.NO_BACKGROUND);
		attachListeners(viewForm, false);

		// toolbarProxy = new ProxyControl(viewForm);
		// viewForm.setTopCenter(toolbarProxy.getControl());
		// contentProxy = new ProxyControl(viewForm);
		// viewForm.setContent(contentProxy.getControl());

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

	// dummy methods from AbstractTabFolder
	// /////////////////////////////////////

	@Override
	public void flushToolbarSize() {
		// viewForm.changed(new Control[] { toolbarProxy.getControl() });
	}

	@Override
	public int getItemCount() {
		// not strictly necessary, just an optimization
		return tabFolder.getItemCount();
	}

	@Override
	public Point getPaneMenuLocation() {
		// TODO Auto-generated method stub
		return super.getPaneMenuLocation();
	}

	@Override
	public Point getPartListLocation() {
		// TODO Auto-generated method stub
		return super.getPartListLocation();
	}

	@Override
	public Point getSystemMenuLocation() {
		// TODO Auto-generated method stub
		return super.getSystemMenuLocation();
	}

	@Override
	public boolean isOnBorder(Point toTest) {
		// TODO Auto-generated method stub
		return super.isOnBorder(toTest);
	}

	@Override
	public void layout(boolean flushCache) {
		Rectangle clientArea = tabFolder.getClientArea();
		viewForm.setBounds(clientArea);
	}

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

	@Override
	public void setState(int state) {
		tabFolder.setMinimized(state == IStackPresentationSite.STATE_MINIMIZED);
		tabFolder.setMaximized(state == IStackPresentationSite.STATE_MAXIMIZED);
		super.setState(state);
	}

	@Override
	public void setTabPosition(int tabPosition) {
		// unused
	}

	@Override
	public void setToolbar(Control toolbarControl) {
		if (toolbarControl != null) {
			toolbarControl.setVisible(false);
		}
		// toolbarProxy.setTargetControl(toolbarControl);
		// viewForm.changed(new Control[] { toolbarProxy.getControl() });
		super.setToolbar(toolbarControl);
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		// contentProxy.setVisible(visible);
	}

	@Override
	public void showMinMax(boolean show) {
		tabFolder.setMaximizeVisible(show);
		tabFolder.setMinimizeVisible(show);
		layout(true);
	}

	// misc
	// /////

	/**
	 * Minimum number characters to display in the tab.
	 * 
	 * @param count
	 *            an integer >= 0
	 */
	public void setMinimumCharacters(int count) {
		tabFolder.setMinimumCharacters(count);
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

	private boolean allowMin(IStackPresentationSite site) {
		return site.supportsState(IStackPresentationSite.STATE_MINIMIZED);
	}

	private boolean allowMax(IStackPresentationSite site) {
		return site.supportsState(IStackPresentationSite.STATE_MAXIMIZED);
	}

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

	// helping classes
	// ////////////////

	private final class ExpandListener implements CTabFolder2Listener,
			SelectionListener {

		public void minimize(CTabFolderEvent event) {
			// event.doit = false;
			fireEvent(TabFolderEvent
					.stackStateToEventId(IStackPresentationSite.STATE_MINIMIZED));
		}

		public void maximize(CTabFolderEvent event) {
			// event.doit = false;
			fireEvent(TabFolderEvent
					.stackStateToEventId(IStackPresentationSite.STATE_MAXIMIZED));
		}

		public void restore(CTabFolderEvent event) {
			// event.doit = false;
			fireEvent(TabFolderEvent
					.stackStateToEventId(IStackPresentationSite.STATE_RESTORED));
		}

		public void close(CTabFolderEvent event) {
			// event.doit = false;
			DefaultTabItem item = (DefaultTabItem) event.item.getData();
			fireEvent(TabFolderEvent.EVENT_CLOSE, item);
		}

		public void showList(CTabFolderEvent event) {
			// event.doit = false;
			fireEvent(TabFolderEvent.EVENT_SHOW_LIST);
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			// unused
		}

		public void widgetSelected(SelectionEvent event) {
			DefaultTabItem item = (DefaultTabItem) event.item.getData();
			Assert.isNotNull(item);
			fireEvent(TabFolderEvent.EVENT_TAB_SELECTED, item);
		}
	}

}
