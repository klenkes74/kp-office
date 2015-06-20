/*
 * Copyright 2015 Kaiserpfalz EDV-Service, Roland T. Lichti
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.kaiserpfalzEdv.offce.ui.accounting.widgets.client

-edv.offce.ui.accounting.widgets.client;

import de.kaiserpfalz-edv.offce.ui.accounting.widgets.MyComponent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.Connect;

// Connector binds client-side widget class to server-side component class
// Connector lives in the client and the @Connect annotation specifies the
// corresponding server-side component
@Connect(MyComponent.class)
public class MyComponentConnector extends AbstractComponentConnector {

	// ServerRpc is used to send events to server. Communication implementation
	// is automatically created here
	MyComponentServerRpc rpc = RpcProxy.create(MyComponentServerRpc.class, this);

	public MyComponentConnector() {
		
		// To receive RPC events from server, we register ClientRpc implementation 
		registerRpc(MyComponentClientRpc.class, new MyComponentClientRpc() {
			public void alert(String message) {
				Window.alert(message);
			}
		});

		// We choose listed for mouse clicks for the widget
		getWidget().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				final MouseEventDetails mouseDetails = MouseEventDetailsBuilder
						.buildMouseEventDetails(event.getNativeEvent(),
								getWidget().getElement());
				
				// When the widget is clicked, the event is sent to server with ServerRpc
				rpc.clicked(mouseDetails);
			}
		});

	}

	// We must implement createWidget() to create correct type of widget
	@Override
	protected Widget createWidget() {
		return GWT.create(MyComponentWidget.class);
	}

	
	// We must implement getWidget() to cast to correct type
	@Override
	public MyComponentWidget getWidget() {
		return (MyComponentWidget) super.getWidget();
	}

	// We must implement getState() to cast to correct type
	@Override
	public MyComponentState getState() {
		return (MyComponentState) super.getState();
	}

	// Whenever the state changes in the server-side, this method is called
	@Override
	public void onStateChanged(StateChangeEvent stateChangeEvent) {
		super.onStateChanged(stateChangeEvent);

		// State is directly readable in the client after it is set in server
		final String text = getState().text;
		getWidget().setText(text);
	}

}
