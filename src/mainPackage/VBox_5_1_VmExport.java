package mainPackage;

import org.virtualbox_5_1.IConsole;
import org.virtualbox_5_1.IMachine;
import org.virtualbox_5_1.IMouse;
import org.virtualbox_5_1.ISession;
import org.virtualbox_5_1.IVirtualBox;
import org.virtualbox_5_1.LockType;
import org.virtualbox_5_1.VirtualBoxManager;

public class VBox_5_1_VmExport {

	public static void main(String[] args) {
		String wsHost = "http://localhost:18083";
		String vmToExport = "CM13";

		System.out.println("Started");
		VirtualBoxManager vboxManager = VirtualBoxManager.createInstance(null);
		vboxManager.connect(wsHost, null, null);
		if (vboxManager.getVBox() != null)
			System.out.println("Connected");
		else {
			System.out.println("Cannot Connect");
			return;
		}

		IVirtualBox vbox = vboxManager.getVBox();
		IMachine vm = vbox.findMachine(vmToExport);
		ISession vSession = vboxManager.getSessionObject();
		vm.lockMachine(vSession, LockType.Shared);
		IConsole vConsole = vSession.getConsole();
		IMouse vMouse = vConsole.getMouse();
		vMouse.putMouseEventAbsolute(10, 9, 0, 0, 0x01);

		System.out.println("Done");
		vboxManager.disconnect(); // only if you are using Web Services
		vboxManager.cleanup();
		System.out.println("Disconnected");

		System.out.println("Finished");
	}
}