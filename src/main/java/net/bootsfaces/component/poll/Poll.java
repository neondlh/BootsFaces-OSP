/**
 *  Copyright 2015-2016 Stephan Rauh (http://www.beyondjava.net)
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

package net.bootsfaces.component.poll;

import java.io.IOException;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;
import javax.faces.event.PreRenderComponentEvent;

import net.bootsfaces.C;
import net.bootsfaces.listeners.AddResourcesListener;
import net.bootsfaces.utils.BsfUtils;

/**
 * The poll component refreshes a portion of the JSF view periodically via AJAX.
 * @author Stephan Rauh
 */

@FacesComponent("net.bootsfaces.component.poll.Poll")
public class Poll extends HtmlCommandButton {

	/**
	 * <p>
	 * The standard component type for this component.
	 * </p>
	 */
	public static final String COMPONENT_TYPE = "net.bootsfaces.component.poll.Poll";
	/**
	 * <p>
	 * The component family for this component.
	 * </p>
	 */
	public static final String COMPONENT_FAMILY = C.BSFCOMPONENT;

	public Poll() {
		setRendererType(null); // this component renders itself
		AddResourcesListener.addBasicJSResource("javax.faces", "jsf.js");
		AddResourcesListener.addThemedCSSResource("tooltip.css");
	}

	public void setValueExpression(String name, ValueExpression binding) {
		name = BsfUtils.snakeCaseToCamelCase(name);
		super.setValueExpression(name, binding);
	}

	public void decode(FacesContext context) {
		if (this.isDisabled()) {
			return;
		}

		String param = this.getClientId(context);
		if (context.getExternalContext().getRequestParameterMap().containsKey(param)) {
			queueEvent(new ActionEvent(this));
		}
	}
	
	@Override
	public void encodeBegin(FacesContext context) throws IOException {
//		super.encodeBegin(context);
	}

	@Override
	public void encodeEnd(FacesContext context) throws IOException {
		if (!isRendered()) {
			return;
		}
		Map<String, Object> attrs = getAttributes();
		ResponseWriter rw = context.getResponseWriter();


        context.getApplication().publishEvent(context,
                                              PreRenderComponentEvent.class,
                                              this);

		String id = getClientId();
		String update = (String) attrs.get("update");
		if (null == update) update="@form";
		String intervalAsString = (String) attrs.get("interval");
		if (null == intervalAsString) intervalAsString="1000";
		String intervalInMillseconds = intervalAsString;
		String execute = (String) attrs.get("execute");
		if (null == execute) execute="@none";
		String once = (String) attrs.get("once");
		boolean isOnce = "true".equalsIgnoreCase(once);

		rw.append("<script id='" + id + "' type='text/javascript'>");
		rw.append("\r\n");
		rw.append("new function() {");
		rw.append("\r\n");
		rw.append("   var pollId;");
		rw.append("\r\n");
		rw.append("  var handleError = function(){clearInterval(pollId);console.log('error with b:poll " + id + "');};");
		rw.append("\r\n");
		rw.append("  pollId = setInterval(function() {");
		rw.append("\r\n");
		if (isOnce) {
			rw.append("    clearInterval(pollId);");
			rw.append("\r\n");
		}
		rw.append("    jsf.ajax.request('" + id + "',null, {'" + id + "':'" + id + "', execute:'" + execute+"', render:'" + update + "', onerror:handleError	});");
		rw.append("\r\n");
		rw.append("  }, " + intervalInMillseconds + ");");
		rw.append("\r\n");
		rw.append("}();");
		rw.append("\r\n");

		
		rw.append("</script>");
//		popComponentFromEL(context);
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

}
