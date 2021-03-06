/*******************************************************************************
 * Copyright (c) 2009, Elias Volanakis
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Elias Volanakis - initial API and implementation
 *******************************************************************************/
package de.volanakis.ribbonide.internal;

import org.eclipse.ui.IWorkbenchWindow;

import com.hexapixel.widgets.ribbon.RibbonButtonGroup;
import com.hexapixel.widgets.ribbon.RibbonGroup;
import com.hexapixel.widgets.ribbon.RibbonTab;

/**
 * Create the 'Edit' Tab, corresponding to the Java perspective.
 */
public final class EditTabBuilder extends AbstractTabBuilder {

	private final IWorkbenchWindow window;

	public EditTabBuilder(AbstractWindowBuilder shellBuilder) {
		super(shellBuilder, "Edit", "org.eclipse.jdt.ui.JavaPerspective");
		window = shellBuilder.getWindow();
	}

	@Override
	protected void fillRibbonTab(RibbonTab tab) {
		createGroupLaunch(tab);
		createGroupOpen(tab);
		createGroupCreate(tab);
		// createGroupEditorTweaks(result);
	}

	// helping methods
	// ////////////////

	private void createGroupCreate(RibbonTab tab) {
		RibbonGroup group = new RibbonGroup(tab, "Create");

		RibbonButtonGroup subgroup1 = new RibbonButtonGroup(group);
		RibbonActionFactory.createNew(subgroup1, window);
		RibbonActionFactory.createNewJava(subgroup1, window);
		RibbonActionFactory.createNewPlugin(subgroup1, window);

		RibbonButtonGroup subgroup2 = new RibbonButtonGroup(group);
		RibbonActionFactory.createNewPackage(subgroup2, window);
		RibbonActionFactory.createNewClass(subgroup2, window);
		RibbonActionFactory.createNewInterface(subgroup2, window);
	}

	private void createGroupLaunch(RibbonTab tab) {
		RibbonGroup group = new RibbonGroup(tab, "Launch");

		RibbonActionFactory.createDebugLast(group, window);
		RibbonActionFactory.createRunLast(group, window);
		RibbonActionFactory.createRunExternal(group, window);
	}

	private void createGroupOpen(RibbonTab tab) {
		RibbonGroup group = new RibbonGroup(tab, "Open");

		RibbonActionFactory.createOpenType(group, window);

		RibbonButtonGroup subgroup = new RibbonButtonGroup(group);
		RibbonActionFactory.createOpenPdeArtifact(subgroup, window);
		RibbonActionFactory.createOpenResource(subgroup, window);
		RibbonActionFactory.createSearch(subgroup, window);
	}

	// private void createGroupEditorTweaks(RibbonTab tab) {
	// RibbonGroup group = new RibbonGroup(tab, "Editor Tweaks");
	//
	// RibbonButtonGroup sub1 = new RibbonButtonGroup(group);
	// RibbonButton rbToggleMarkOccurences = new RibbonButton(sub1, ICE
	// .getImage("mark_occurrences.gif"), null,
	// RibbonButton.STYLE_TOGGLE);
	// // org.eclipse.ui.edit.text.toggleBlockSelectionMode
	// RibbonButton rbToggleBlockSelection = new RibbonButton(sub1, ICE
	// .getImage("block_selection_mode.gif"), null,
	// RibbonButton.STYLE_TOGGLE);
	//
	// RibbonButtonGroup sub2 = new RibbonButtonGroup(group);
	// RibbonButton rbToggleBreadcrumb = new RibbonButton(sub2, ICE
	// .getImage("toggle_breadcrumb.gif"), null,
	// RibbonButton.STYLE_TOGGLE);
	// RibbonButton rbShowWhitespace = new RibbonButton(sub2, ICE
	// .getImage("show_whitespace_chars.gif"), null,
	// RibbonButton.STYLE_TOGGLE);
	//
	// RibbonButtonGroup sub3 = new RibbonButtonGroup(group);
	// RibbonButton rbFoldUninterestingElems = new RibbonButton(sub3, ICE
	// .getImage("interest-folding.gif"), null,
	// RibbonButton.STYLE_TOGGLE);
	// RibbonButton rbPinEditor = new RibbonButton(sub3, ICE
	// .getImage("pin_editor.gif"), null, RibbonButton.STYLE_TOGGLE);
	// }

}
