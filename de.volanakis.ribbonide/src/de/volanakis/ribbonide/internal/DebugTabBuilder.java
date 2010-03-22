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

import com.hexapixel.widgets.ribbon.RibbonButton;
import com.hexapixel.widgets.ribbon.RibbonGroup;
import com.hexapixel.widgets.ribbon.RibbonTab;
import com.hexapixel.widgets.ribbon.RibbonToolbar;
import com.hexapixel.widgets.ribbon.RibbonToolbarGrouping;

import de.volanakis.ribbonide.internal.d.ICD;
import de.volanakis.ribbonide.internal.e.ICE;

/**
 * Create the 'Debug' Tab, corresponding to the Debug perspective.
 */
public final class DebugTabBuilder extends AbstractTabBuilder {

	private final IWorkbenchWindow window;

	public DebugTabBuilder(AbstractWindowBuilder shellBuilder) {
		super(shellBuilder, "Debug", "org.eclipse.debug.ui.DebugPerspective");
		window = shellBuilder.getWindow();
	}

	@Override
	protected void fillRibbonTab(RibbonTab tab) {
		createGroupLaunch(tab);
		createGroupDebug(tab);
		createGroupNavigation(tab);
	}

	// helping methods
	// ///////////////

	private void createGroupDebug(RibbonTab tab) {
		RibbonGroup group = new RibbonGroup(tab, "Debug");

		RibbonToolbar toolbar = new RibbonToolbar(group,
				RibbonToolbar.STYLE_BORDERED, 2);

		RibbonToolbarGrouping rtgFlow1 = new RibbonToolbarGrouping(toolbar, 1);
		RibbonActionFactory.createResume(rtgFlow1, window);
		RibbonActionFactory.createSuspend(rtgFlow1, window);
		RibbonActionFactory.createTerminate(rtgFlow1, window);
		RibbonActionFactory.createTerminateAndRestart(rtgFlow1, window);

		RibbonToolbarGrouping rtgFlow2 = new RibbonToolbarGrouping(toolbar, 1);
		RibbonActionFactory.createStepInto(rtgFlow2, window);
		RibbonActionFactory.createStepOver(rtgFlow2, window);
		RibbonActionFactory.createStepReturn(rtgFlow2, window);
		// TODO [ev] try this later -- tracking enablement of RTL is not
		// straightforward
		// RibbonActionFactory.createRunToLine(rtgFlow2, window);

		RibbonToolbarGrouping rtgFlow3 = new RibbonToolbarGrouping(toolbar, 1);
		RibbonActionFactory.createDropToFrame(rtgFlow3, window);

		RibbonToolbarGrouping rtgBP = new RibbonToolbarGrouping(toolbar, 2);
		RibbonButton rbToggleBP = new RibbonButton(rtgBP, ICE
				.getImage("brkp_obj.gif"), ICE.getImage("brkpd_obj.gif"),
				RibbonButton.STYLE_PUSH);
		RibbonButton rbSkipAllBPs = new RibbonButton(rtgBP, ICE
				.getImage("skip_brkp.gif"), ICD.getImage("skip_brkp.gif"),
				RibbonButton.STYLE_TOGGLE);
		RibbonButton rbRemoveAllBPs = new RibbonButton(rtgBP, ICE
				.getImage("rem_all_co.gif"), ICD.getImage("rem_all_co.gif"),
				RibbonButton.STYLE_PUSH);
		RibbonButton rbAddExceptionBP = new RibbonButton(rtgBP, ICE
				.getImage("exc_catch.gif"), ICD.getImage("exc_catch.gif"),
				RibbonButton.STYLE_PUSH);

		RibbonToolbarGrouping rtgOther = new RibbonToolbarGrouping(toolbar, 2);
		RibbonButton rbInspect = new RibbonButton(rtgOther, ICE
				.getImage("insp_sbook.gif"), ICD.getImage("insp_sbook.gif"),
				RibbonButton.STYLE_PUSH);
		RibbonButton rbExpression = new RibbonButton(rtgOther, ICE
				.getImage("watch_exp.gif"), ICD.getImage("watch_exp.gif"),
				RibbonButton.STYLE_PUSH);

		// TODO [ev] enable once hooked up
		rbToggleBP.setEnabled(false);
		rbSkipAllBPs.setEnabled(false);
		rbRemoveAllBPs.setEnabled(false);
		rbAddExceptionBP.setEnabled(false);
		rbInspect.setEnabled(false);
		rbExpression.setEnabled(false);
	}

	private void createGroupLaunch(RibbonTab tab) {
		RibbonGroup group = new RibbonGroup(tab, "Launch");

		RibbonActionFactory.createDebugLast(group, window);
		RibbonActionFactory.createRunLast(group, window);
		RibbonActionFactory.createRunExternal(group, window);
	}

	private void createGroupNavigation(RibbonTab tab) {
		RibbonGroup group = new RibbonGroup(tab, "Navigate");

		RibbonToolbar toolbar = new RibbonToolbar(group,
				RibbonToolbar.STYLE_BORDERED, 2);

		RibbonToolbarGrouping rtgNavigate = new RibbonToolbarGrouping(toolbar,
				1);
		RibbonActionFactory.createGoLastEdit(rtgNavigate, window);
		RibbonActionFactory.createGoBackward(rtgNavigate, window);
		RibbonActionFactory.createGoForward(rtgNavigate, window);

		RibbonToolbarGrouping rtgAnnot = new RibbonToolbarGrouping(toolbar, 2);
		RibbonActionFactory.createGoNext(rtgAnnot, window);
		RibbonActionFactory.createGoPrevious(rtgAnnot, window);
	}
}
