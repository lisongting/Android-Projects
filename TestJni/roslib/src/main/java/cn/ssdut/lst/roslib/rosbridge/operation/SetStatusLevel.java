/**
 * Copyright (c) 2014 Jilk Systems, Inc.
 * 
 * This file is part of the Java ROSBridge Client.
 *
 * The Java ROSBridge Client is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The Java ROSBridge Client is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the Java ROSBridge Client.  If not, see http://www.gnu.org/licenses/.
 * 
 */
package cn.ssdut.lst.roslib.rosbridge.operation;

import cn.ssdut.lst.roslib.message.MessageType;

@MessageType(string = "set_level")
public class SetStatusLevel extends Operation {
    public String level;
    
    public SetStatusLevel() {}
    
    public SetStatusLevel(String level) {
        this.level = null;
        if ("none".equals(level) ||
                "warning".equals(level) ||
                "error".equals(level) ||
                "info".equals(level))
            this.level = level;
    }
}