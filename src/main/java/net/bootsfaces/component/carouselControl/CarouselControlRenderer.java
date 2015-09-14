/**
 *  Copyright 2014-15 by Riccardo Massera (TheCoder4.Eu) and Stephan Rauh (http://www.beyondjava.net).
 *  
 *  This file is part of BootsFaces.
 *  
 *  BootsFaces is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  BootsFaces is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with BootsFaces. If not, see <http://www.gnu.org/licenses/>.
 */

package net.bootsfaces.component.carouselControl;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import net.bootsfaces.component.ajax.AJAXRenderer;
import net.bootsfaces.render.CoreRenderer;
import net.bootsfaces.render.Tooltip;

/** This class generates the HTML code of &lt;b:carouselControl /&gt;. */
@FacesRenderer(componentFamily = "net.bootsfaces.component", rendererType = "net.bootsfaces.component.carouselControl.CarouselControl")
public class CarouselControlRenderer extends CoreRenderer {
	/**
	 * This methods receives and processes input made by the user. More
	 * specifically, it checks whether the user has interacted with the current
	 * b:carouselControl. The default implementation simply stores the input
	 * value in the list of submitted values. If the validation checks are
	 * passed, the values in the <code>submittedValues</code> list are store in
	 * the backend bean.
	 * 
	 * @param context
	 *            the FacesContext.
	 * @param component
	 *            the current b:carouselControl.
	 */
	@Override
	public void decode(FacesContext context, UIComponent component) {
		CarouselControl carouselControl = (CarouselControl) component;

		if (carouselControl.isDisabled()) {
			return;
		}

		new AJAXRenderer().decode(context, component);
	}

	/**
	 * This methods generates the HTML code of the current b:carouselControl.
	 * <code>encodeBegin</code> generates the start of the component. After the,
	 * the JSF framework calls <code>encodeChildren()</code> to generate the
	 * HTML code between the beginning and the end of the component. For
	 * instance, in the case of a panel component the content of the panel is
	 * generated by <code>encodeChildren()</code>. After that,
	 * <code>encodeEnd()</code> is called to generate the rest of the HTML code.
	 * 
	 * @param context
	 *            the FacesContext.
	 * @param component
	 *            the current b:carouselControl.
	 * @throws IOException
	 *             thrown if something goes wrong when writing the HTML code.
	 */
	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {

	}

	public void myEncodeBegin(FacesContext context, UIComponent component) throws IOException {
		if (!component.isRendered()) {
			return;
		}
		CarouselControl carouselControl = (CarouselControl) component;
		ResponseWriter rw = context.getResponseWriter();
		String clientId = carouselControl.getClientId();

		/**
		 * <a class=
		 * "left carousel-control" href="#myCarousel" role="button" data-slide=
		 * "prev"> <span class=
		 * "glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
		 * <span class="sr-only">Previous</span> </a>
		 */

		// put custom code here
		// Simple demo widget that simply renders every attribute value
		rw.startElement("carouselControl", carouselControl);
		Tooltip.generateTooltip(context, carouselControl, rw);

		rw.writeAttribute("id", carouselControl.getId(), "id");
		Tooltip.activateTooltips(context, carouselControl);
		AJAXRenderer.generateBootsFacesAJAXAndJavaScript(context, carouselControl, rw);

		UIComponent parent = carouselControl.getParent();
		rw.writeAttribute("href", "#" + parent.getClientId(), "href");
		rw.writeAttribute("role", "button", "role");

		String direction = carouselControl.getDirection();
		rw.writeAttribute("data-slide", direction, "data-slide");
		String styleClass = direction + " carousel-control";
		if (null != carouselControl.getStyleClass())
			styleClass += " " + carouselControl.getStyleClass();
		rw.writeAttribute("styleClass", styleClass, "styleClass");

		rw.writeAttribute("style", carouselControl.getStyle(), "style");
	}

	/**
	 * This methods generates the HTML code of the current b:carouselControl.
	 * <code>encodeBegin</code> generates the start of the component. After the,
	 * the JSF framework calls <code>encodeChildren()</code> to generate the
	 * HTML code between the beginning and the end of the component. For
	 * instance, in the case of a panel component the content of the panel is
	 * generated by <code>encodeChildren()</code>. After that,
	 * <code>encodeEnd()</code> is called to generate the rest of the HTML code.
	 * 
	 * @param context
	 *            the FacesContext.
	 * @param component
	 *            the current b:carouselControl.
	 * @throws IOException
	 *             thrown if something goes wrong when writing the HTML code.
	 */
	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {

	}

	public void myEncodeEnd(FacesContext context, UIComponent component) throws IOException {
		if (!component.isRendered()) {
			return;
		}
		CarouselControl carouselControl = (CarouselControl) component;
		ResponseWriter rw = context.getResponseWriter();
		rw.endElement("a");
		Tooltip.activateTooltips(context, carouselControl);
	}

	public void encodeDefaultControls(FacesContext context, UIComponent component, String clientId) throws IOException {
		ResponseWriter rw = context.getResponseWriter();
		{
			rw.startElement("a", component);
			rw.writeAttribute("class", "left carousel-control", "class");
			rw.writeAttribute("href", "#"+clientId, "href");
			rw.writeAttribute("role", "button", "role");
			rw.writeAttribute("data-slide", "prev", "data-slide");
			{
				rw.startElement("span", component);
				rw.writeAttribute("class", "glyphicon glyphicon-chevron-left", "class");
				rw.writeAttribute("aria-hidden", "true", "aria-hidden");
				rw.endElement("span");
			}
			{
				rw.startElement("span", component);
				rw.writeAttribute("class", "sr-only", "class");
				rw.append("Previous");
				rw.endElement("span");
			}
			rw.endElement("a");
		}	
		{
			rw.startElement("a", component);
			rw.writeAttribute("class", "right carousel-control", "class");
			rw.writeAttribute("href", "#"+clientId, "href");
			rw.writeAttribute("role", "button", "role");
			rw.writeAttribute("data-slide", "next", "data-slide");
			{
				rw.startElement("span", component);
				rw.writeAttribute("class", "glyphicon glyphicon-chevron-right", "class");
				rw.writeAttribute("aria-hidden", "true", "aria-hidden");
				rw.endElement("span");
			}
			{
				rw.startElement("span", component);
				rw.writeAttribute("class", "sr-only", "class");
				rw.append("Next");
				rw.endElement("span");
			}
			rw.endElement("a");
		}	
	}
}
