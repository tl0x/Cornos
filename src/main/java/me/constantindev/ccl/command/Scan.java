/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Scan
# Created by constantin at 16:40, Mär 12 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.command;

import me.constantindev.ccl.etc.PScanRunner;
import me.constantindev.ccl.etc.PortScannerManager;
import me.constantindev.ccl.etc.base.Command;
import me.constantindev.ccl.etc.helper.STL;

import java.net.InetAddress;

public class Scan extends Command {
    public Scan() {
        super("Scan", "Scans the given server for ports in range", new String[]{"scan"});
    }

    @Override
    public void onExecute(String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("cancel")) {
                STL.notifyUser("Cancelled all scans");
                PortScannerManager.killAllScans();
                return;
            }
        }
        if (args.length < 5) {
            STL.notifyUser("I'd appreciate you giving me an ip, port min, port max, thread amount and delay between scans");
            STL.notifyUser("Example: scan localhost 0 65535 5 3");
            return;
        }
        if (!STL.tryParseI(args[1]) || !STL.tryParseI(args[2]) || !STL.tryParseI(args[3]) || !STL.tryParseI(args[4])) {
            STL.notifyUser("Homie check the numbers");
            return;
        }
        InetAddress addr;
        try {
            addr = InetAddress.getByName(args[0]);
        } catch (Exception exc) {
            STL.notifyUser("idk if thats a valid address ngl");
            return;
        }
        STL.notifyUser("Started scanning " + args[0]);
        double minTime = (Double.parseDouble(args[2]) - Double.parseDouble(args[1])) * Double.parseDouble(args[4]);
        double maxTime = (Double.parseDouble(args[2]) - Double.parseDouble(args[1])) * 200 * Double.parseDouble(args[4]);
        STL.notifyUser("Times:");
        STL.notifyUser("  Best case:  " + (minTime / 1000 / Double.parseDouble(args[3])) + " seconds");
        STL.notifyUser("  worst case: " + (maxTime / 1000 / Double.parseDouble(args[3])) + " seconds");
        PScanRunner pscR = new PScanRunner(addr, Integer.parseInt(args[3]), Integer.parseInt(args[4]), 200, Integer.parseInt(args[1]), Integer.parseInt(args[2]), scanResults -> {
            STL.notifyUser("Done!");
            int ports = 0;
            for (PortScannerManager.ScanResult result : scanResults) {
                if (result.isOpen()) {
                    STL.notifyUser("Port " + result.getPort() + " is open");
                    ports++;
                }
            }
            STL.notifyUser("Total ports: " + scanResults.size());
            STL.notifyUser("Open ports: " + ports);
            STL.notifyUser("% ports: " + (((double) ports / scanResults.size()) * 100));
        });
        PortScannerManager.scans.add(pscR);
        super.onExecute(args);
    }
}
