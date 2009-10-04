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
		RibbonButton rbResume = new RibbonButton(rtgFlow1, ICE
				.getImage("resume_co.gif"), ICD.getImage("resume_co.gif"),
				RibbonButton.STYLE_PUSH);
		RibbonButton rbSuspend = new RibbonButton(rtgFlow1, ICE
				.getImage("suspend_co.gif"), ICD.getImage("suspend_co.gif"),
				RibbonButton.STYLE_PUSH);
		RibbonButton rbTerminate = new RibbonButton(rtgFlow1, ICE
				.getImage("terminate_co.gif"),
				ICD.getImage("terminate_co.gif"), RibbonButton.STYLE_PUSH);
		RibbonButton rbTerminateRel = new RibbonButton(rtgFlow1, ICE
				.getImage("term_restart_ev.gif"), ICD
				.getImage("term_restart_ev.gif"), RibbonButton.STYLE_PUSH);

		RibbonToolbarGrouping rtgFlow2 = new RibbonToolbarGrouping(toolbar, 1);
		RibbonButton rbStepInto = new RibbonButton(rtgFlow2, ICE
				.getImage("stepinto_co.gif"), ICD.getImage("stepinto_co.gif"),
				RibbonButton.STYLE_PUSH);
		RibbonButton rbStepOver = new RibbonButton(rtgFlow2, ICE
				.getImage("stepover_co.gif"), ICD.getImage("stepover_co.gif"),
				RibbonButton.STYLE_PUSH);
		RibbonButton rbStepReturn = new RibbonButton(rtgFlow2, ICE
				.getImage("stepreturn_co.gif"), ICD
				.getImage("stepreturn_co.gif"), RibbonButton.STYLE_PUSH);
		RibbonButton rbRunToLine = new RibbonButton(rtgFlow2, ICE
				.getImage("runtoline_co.gif"),
				ICD.getImage("runtoline_co.gif"), RibbonButton.STYLE_PUSH);

		RibbonToolbarGrouping rtgFlow3 = new RibbonToolbarGrouping(toolbar, 1);
		RibbonButton rbDropToFrame = new RibbonButton(rtgFlow3, ICE
				.getImage("drop_to_frame.gif"), ICD
				.getImage("drop_to_frame.gif"), RibbonButton.STYLE_PUSH);

		RibbonToolbarGrouping rtgBP = new RibbonToolbarGrouping(toolbar, 2);
		RibbonButton rbToggleBP = new RibbonButton(rtgBP, ICE
				.getImage("brkp_obj.gif"), null, RibbonButton.STYLE_PUSH);
		RibbonButton rbSkipAllBPs = new RibbonButton(rtgBP, ICE
				.getImage("skip_brkp.gif"), null, RibbonButton.STYLE_TOGGLE);
		RibbonButton rbRemoveAllBPs = new RibbonButton(rtgBP, ICE
				.getImage("rem_all_co.gif"), ICD.getImage("rem_all_co.gif"),
				RibbonButton.STYLE_PUSH);
		RibbonButton rbAddExceptionBP = new RibbonButton(rtgBP, ICE
				.getImage("exc_catch.gif"), null, RibbonButton.STYLE_PUSH);

		RibbonToolbarGrouping rtgOther = new RibbonToolbarGrouping(toolbar, 2);
		RibbonButton rbInspect = new RibbonButton(rtgOther, ICE
				.getImage("insp_sbook.gif"), null, RibbonButton.STYLE_PUSH);
		RibbonButton rbExpression = new RibbonButton(rtgOther, ICE
				.getImage("watch_exp.gif"), null, RibbonButton.STYLE_PUSH);
	}

	private void createGroupLaunch(RibbonTab tab) {
		RibbonGroup group = new RibbonGroup(tab, "Launch");

		RibbonButton rbDebug = new RibbonButton(group, ICE
				.getImage("debug_exc_30.png"), "Debug",
				RibbonButton.STYLE_ARROW_DOWN_SPLIT);
		RibbonButton rbRun = new RibbonButton(group, ICE
				.getImage("run_exc_30.png"), "Run ",
				RibbonButton.STYLE_ARROW_DOWN_SPLIT);
		RibbonButton rbRunExt = new RibbonButton(group, ICE
				.getImage("external_tools_ev_30.png"), "Run Ext",
				RibbonButton.STYLE_ARROW_DOWN_SPLIT);
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
