/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.internal.logging.events;

import org.gradle.api.logging.LogLevel;
import org.gradle.internal.UncheckedException;

import javax.annotation.Nullable;

public class RemoveListenerEvent extends OutputEvent {
    private final OutputEventListener listener;
    private boolean handled;

    public RemoveListenerEvent(OutputEventListener listener) {
        this.listener = listener;
    }

    public OutputEventListener getListener() {
        return listener;
    }

    @Nullable
    @Override
    public LogLevel getLogLevel() {
        return null;
    }

    public void handled() {
        synchronized (this) {
            handled = true;
            notifyAll();
        }
    }

    public void waitUntilHandled() {
        synchronized (this) {
            while (!handled) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw UncheckedException.throwAsUncheckedException(e);
                }
            }
        }
    }
}