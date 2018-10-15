/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
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

package com.github.shvul.wda.client.support;

import org.apache.http.util.Args;

import java.util.ArrayList;
import java.util.List;

public class IOSDeploy {

    private static final String IOS_DEPLOY = "ios-deploy";
    private String udid;

    public IOSDeploy(String udid) {
        Args.notNull(udid, "Device udid");
        this.udid = udid;
    }

    public void removeApp(String bundleId) {
        List<String> command = new ArrayList<>();
        command.add(IOS_DEPLOY);
        command.add(IOSDeployKey.UNINSTALL_ONLY.getKey());
        command.add(IOSDeployKey.ID.getKey());
        command.add(udid);
        command.add(IOSDeployKey.BUNDLE_ID.getKey());
        command.add(bundleId);
        CommandLineExecutor.execute(command.toArray(new String[command.size()]));
    }

    public void installApp(String appPath) {
        List<String> command = new ArrayList<>();
        command.add(IOS_DEPLOY);
        command.add(IOSDeployKey.ID.getKey());
        command.add(udid);
        command.add(IOSDeployKey.BUNDLE.getKey());
        command.add(appPath);
        CommandLineExecutor.execute(command.toArray(new String[command.size()]));
    }

    public boolean isAppInstalled(String bundleId) {
        List<String> command = new ArrayList<>();
        command.add(IOS_DEPLOY);
        command.add(IOSDeployKey.EXISTS.getKey());
        command.add(IOSDeployKey.ID.getKey());
        command.add(udid);
        command.add(IOSDeployKey.BUNDLE_ID.getKey());
        command.add(bundleId);
        return Boolean.valueOf(CommandLineExecutor.execute(command.toArray(new String[command.size()])));
    }

    private enum IOSDeployKey {
        ID("--id"),
        BUNDLE_ID("--bundle_id"),
        BUNDLE("--bundle"),
        LAUNCH("--justlaunch"),
        UNINSTALL_ONLY("--uninstall_only"),
        UNINSTALL("--uninstall"),
        EXISTS("--exists");

        private String key;

        IOSDeployKey(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }
}
