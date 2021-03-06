/*
 * Created on Feb 21, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package no.ntnu.kjerne.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ntnu.kjerne.model.Person;
import no.ntnu.kjerne.model.Project;

/**
 * Implements action for removing persons from the list.
 * 
 * @author Thomas &Oslash;sterlie
 * 
 * @version $Revision: 1.1 $ - $Date: 2005/02/22 07:57:39 $
 */
public class RemovePersonAction extends AbstractAction {

	/**
	 * Parent component.
	 */
	private ProjectPanel projectPanel;

	/**
	 * Default constructor.  Initialises member variables.
	 * 
	 * @param projectPanel Parent component.
	 */
	public RemovePersonAction(ProjectPanel projectPanel) {
		this.projectPanel = projectPanel;
	}
	
	/**
	 * Invoked when an action occurs.
	 * 
	 * @param e The action event.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		PersonListModel plm = projectPanel.getModel();
		Project project = plm.getProject();

		int index = projectPanel.getSelectedElement();
		Person person = project.getPerson(index);
		project.removePerson(person);
		
	}
}
