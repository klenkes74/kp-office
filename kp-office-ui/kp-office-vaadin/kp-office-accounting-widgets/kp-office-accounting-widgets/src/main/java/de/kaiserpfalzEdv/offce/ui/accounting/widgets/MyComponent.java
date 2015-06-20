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

package de.kaiserpfalzEdv.offce.ui.accounting.widgets

-edv.offce.ui.accounting.widgets;

import de.kaiserpfalz-edv.offce.ui.accounting.widgets.client.MyComponentClientRpc;
import de.kaiserpfalz-edv.offce.ui.accounting.widgets.client.MyComponentServerRpc;
import de.kaiserpfalz-edv.offce.ui.accounting.widgets.client.MyComponentState;

import com.vaadin.shared.MouseEventDetails;

// This is the server-side UI component that provides public API 
// for MyComponent
public class MyComponent extends com.vaadin.ui.AbstractComponent {

	private int clickCount = 0;

	// To process events from the client, we implement ServerRpc
	private MyComponentServerRpc rpc = new MyComponentServerRpc() {

		// Event received from client - user clicked our widget
		public void clicked(MouseEventDetails mouseDetails) {
			
			// Send nag message every 5:th click with ClientRpc
			if (++clickCount % 5 == 0) {
				getRpcProxy(MyComponentClientRpc.class)
						.alert("Ok, that's enough!");
			}
			
			// Update shared state. This state update is automatically 
			// sent to the client. 
			getState().text = "You have clicked " + clickCount + " times";
		}
	};

	public MyComponent() {

		// To receive events from the client, we register ServerRpc
		registerRpc(rpc);
	}

	// We must override getState() to cast the state to MyComponentState
	@Override
	public MyComponentState getState() {
		return (MyComponentState) super.getState();
	}
}
