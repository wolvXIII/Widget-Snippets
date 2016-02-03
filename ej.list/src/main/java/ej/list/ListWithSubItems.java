/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package ej.list;

import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.container.List;
import ej.container.Scroll;
import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.style.Stylesheet;
import ej.style.background.PlainBackground;
import ej.style.border.ComplexRectangularBorder;
import ej.style.font.FontProfile;
import ej.style.font.FontProfile.FontSize;
import ej.style.outline.EmptyOutline;
import ej.style.outline.SimpleOutline;
import ej.style.selector.ClassSelector;
import ej.style.selector.TypeSelector;
import ej.style.selector.combinator.AndCombinator;
import ej.style.util.EditableStyle;
import ej.widget.StyledDesktop;
import ej.widget.StyledPanel;
import ej.widget.basic.Label;

public class ListWithSubItems {

	private static final String ITEM = "Item";
	private static final String SUB_ITEM = "SubItem";

	public static void main(String[] args) {
		MicroUI.start();
		createStylesheet();
		createPage();
	}

	private static void createStylesheet() {
		Stylesheet stylesheet = ServiceLoaderFactory.getServiceLoader().getService(Stylesheet.class);

		// Remove white background from all elements.
		EditableStyle defaultStyle = new EditableStyle();
		defaultStyle.setBorder(EmptyOutline.EMPTY_OUTLINE);
		stylesheet.setDefaultStyle(defaultStyle);

		// Add a white background to the panel.
		// StyledPanel {
		EditableStyle panelStyle = new EditableStyle();
		// background-color: black;
		PlainBackground panelBackground = new PlainBackground(Colors.WHITE);
		panelStyle.setBorder(panelBackground);
		// }
		stylesheet.addRule(new TypeSelector(StyledPanel.class), panelStyle);

		// Create the list item style.
		// .Item {
		EditableStyle itemStyle = new EditableStyle();
		// font-size: medium;
		FontProfile itemFontProfile = new FontProfile();
		itemFontProfile.setSize(FontSize.MEDIUM);
		itemStyle.setFontProfile(itemFontProfile);
		// border-bottom: 1px solid gray;
		ComplexRectangularBorder itemBorder = new ComplexRectangularBorder();
		itemBorder.setBottom(1);
		itemBorder.setColorBottom(Colors.GRAY);
		itemStyle.setBorder(itemBorder);
		// padding: 6px;
		SimpleOutline itemPadding = new SimpleOutline(6);
		itemStyle.setPadding(itemPadding);
		// }
		stylesheet.addRule(new ClassSelector(ITEM), itemStyle);

		// Create the list sub item style.
		// .SubItem {
		EditableStyle subItemStyle = new EditableStyle();
		// color: gray;
		subItemStyle.setForegroundColor(Colors.GRAY);
		// font-size: small;
		FontProfile subItemFontProfile = new FontProfile();
		subItemFontProfile.setSize(FontSize.SMALL);
		subItemStyle.setFontProfile(subItemFontProfile);
		// }
		stylesheet.addRule(new AndCombinator(new TypeSelector(Label.class), new ClassSelector(SUB_ITEM)), subItemStyle);

	}

	private static void createPage() {
		// Create top-level containers.
		StyledDesktop desktop = new StyledDesktop();
		StyledPanel panel = new StyledPanel();

		// Create the list that will scroll.
		List listComposite = new List(false);

		for (int i = 0; ++i <= 20;) {
			// Create the list items:
			// - the main item,
			Label item = new Label("Item " + i);
			// - the sub item,
			Label subItem = new Label("Sub " + i);
			subItem.addClassSelector(SUB_ITEM);
			// - the item container (a list).
			List itemComposite = new List(false);
			itemComposite.addClassSelector(ITEM);
			itemComposite.add(item);
			itemComposite.add(subItem);
			// Add it to the scroll list.
			listComposite.add(itemComposite);
		}

		// Create the scroll composite containing the list…
		Scroll scrollComposite = new Scroll(false, listComposite, true);
		// … and add it to the panel.
		panel.setWidget(scrollComposite);

		// Show everything.
		panel.show(desktop, true);
		desktop.show();
	}

}
