/*
 * Sébastien Eon 2016 / CC0-1.0
 */
package sew.list;

import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.container.List;
import ej.container.Scroll;
import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.style.Stylesheet;
import ej.style.background.NoBackground;
import ej.style.background.PlainBackground;
import ej.style.border.ComplexRectangularBorder;
import ej.style.font.FontProfile;
import ej.style.font.FontProfile.FontSize;
import ej.style.outline.SimpleOutline;
import ej.style.selector.ClassSelector;
import ej.style.selector.TypeSelector;
import ej.style.selector.combinator.AndCombinator;
import ej.style.util.EditableStyle;
import ej.widget.StyledDesktop;
import ej.widget.StyledPanel;
import ej.widget.basic.Label;
import ej.widget.composed.Button;
import ej.widget.listener.OnClickListener;

public class ListExtended {

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
		defaultStyle.setBackground(NoBackground.NO_BACKGROUND);
		stylesheet.setDefaultStyle(defaultStyle);

		// Add a white background to the panel.
		// StyledPanel {
		EditableStyle panelStyle = new EditableStyle();
		// background-color: white;
		panelStyle.setBackground(new PlainBackground());
		panelStyle.setBackgroundColor(Colors.WHITE);
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
		ComplexRectangularBorder itemBorder = new ComplexRectangularBorder(0, 0, 1, 0);
		itemStyle.setBorder(itemBorder);
		itemStyle.setBorderColor(Colors.GRAY);
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
			final String buttonText = "Item " + i;
			final Button item = new Button("+ " + buttonText);
			// - the sub item,
			final Label subItem = new Label("Sub " + i);
			subItem.addClassSelector(SUB_ITEM);
			subItem.setVisible(false);
			// - the item container (a list).
			final List itemComposite = new List(false);
			itemComposite.addClassSelector(ITEM);
			itemComposite.add(item);
			itemComposite.add(subItem);
			// Add it to the scroll list.
			listComposite.add(itemComposite);
			item.addOnClickListener(new OnClickListener() {
				@Override
				public void onClick() {
					if (subItem.isVisible()) {
						item.setText("+ " + buttonText);
						subItem.setVisible(false);
					} else {
						item.setText("-  " + buttonText);
						subItem.setVisible(true);
					}
					itemComposite.revalidate();
				}
			});
		}

		// Create the scroll composite containing the list…
		Scroll scrollComposite = new Scroll(false, true);
		scrollComposite.setWidget(listComposite);
		// … and add it to the panel.
		panel.setWidget(scrollComposite);

		// Show everything.
		panel.show(desktop, true);
		desktop.show();
	}

}
