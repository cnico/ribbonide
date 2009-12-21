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

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.CommandEvent;
import org.eclipse.core.commands.ICommandListener;
import org.eclipse.core.commands.common.CommandException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;

import com.hexapixel.widgets.ribbon.AbstractRibbonGroupItem;
import com.hexapixel.widgets.ribbon.IDisposeListener;
import com.hexapixel.widgets.ribbon.RibbonButton;
import com.hexapixel.widgets.ribbon.RibbonTooltip;

/**
 * Connects a {@link RibbonButton} with a {@link Command}.
 */
public final class CommandButton extends SelectionAdapter implements
		ICommandListener, IDisposeListener {

	private final RibbonButton button;
	private final String commandId;
	private final Command command;

	private ListenerList menuListeners;
	private boolean removeAll;
	private boolean trace;

	public CommandButton(RibbonButton button, String commandId) {
		this.button = button;
		Assert.isNotNull(commandId);
		this.commandId = commandId;

		command = getCommandService().getCommand(commandId);
		if (!command.isDefined()) {
			Activator.logWarning(new Exception("Undefined command: "
					+ commandId));
		}
		button.addDisposeListener(this);
		command.addCommandListener(this);
		boolean enabled = command.isDefined() ? command.isEnabled() : false;
		button.setEnabled(enabled);
		button.addSelectionListener(this);
		updateTooltip();
	}

	public void addMenuListener(IMenuListener listener) {
		Assert.isNotNull(listener);
		if (menuListeners == null) {
			menuListeners = new ListenerList();
		}
		menuListeners.add(listener);
	}

	public void commandChanged(CommandEvent commandEvent) {
		trace("CC: %s - d:%s, e:%s, h:%s", commandId, command.isDefined(),
				command.isEnabled(), command.isHandled());
		if (commandEvent.isEnabledChanged()) {
			boolean isEnabled = commandEvent.getCommand().isEnabled();
			trace("enabl: %s? %s", commandId, isEnabled);
			button.setEnabled(isEnabled);
		}
		if (commandEvent.isNameChanged() || commandEvent.isDescriptionChanged()) {
			updateTooltip();
		}
	}

	public void itemDisposed(AbstractRibbonGroupItem item) {
		button.removeSelectionListener(this);
		command.removeCommandListener(this);
	}

	public void removeMenuListener(IMenuListener listener) {
		if (menuListeners != null) {
			menuListeners.remove(listener);
		}
	}

	// helping methods
	// ////////////////

	public void setRemoveAllWhenShown(boolean removeAll) {
		this.removeAll = removeAll;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		trace("sel: %s - e:%s, h:%s", commandId, command.isEnabled(), command
				.isHandled());
		if (!button.isBottomSelected()) {
			button.setSelected(false);
			try {
				getHandlerService().executeCommand(commandId, null);
				if (button.isEnabled() != command.isEnabled()) {
					button.setEnabled(command.isEnabled());
				}
			} catch (CommandException cex) {
				Activator.log(cex);
			}
		} else {
			notifyMenuListeners();
			button.showMenu();
		}
	}

	private ICommandService getCommandService() {
		return (ICommandService) PlatformUI.getWorkbench().getService(
				ICommandService.class);
	}

	private IHandlerService getHandlerService() {
		return (IHandlerService) PlatformUI.getWorkbench().getService(
				IHandlerService.class);
	}

	private void notifyMenuListeners() {
		final Menu menu = button.getMenu();
		if (removeAll) {
			for (Item item : menu.getItems()) {
				item.dispose();
			}
		}
		if (menuListeners != null) {
			for (final Object listener : menuListeners.getListeners()) {
				SafeRunner.run(new ISafeRunnable() {
					public void handleException(Throwable exception) {
						// unused
					}

					public void run() throws Exception {
						((IMenuListener) listener).menuAboutToShow(menu);
					}
				});
			}
		}
	}

	void setTrace(boolean trace) {
		this.trace = trace;
	}

	private void trace(String format, Object... args) {
		if (trace) {
			Activator.trace(format, args);
		}
	}

	private void updateTooltip() {
		String title = null;
		String descr = null;
		try {
			title = command.getName();
			try {
				descr = command.getDescription();
			} catch (NotDefinedException nde) {
				descr = title;
			}
		} catch (NotDefinedException e) {
			// ignore
		}
		if (title != null) {
			button.setToolTip(new RibbonTooltip(title, descr));
		}
	}
}