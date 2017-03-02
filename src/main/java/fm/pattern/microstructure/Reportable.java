/*
 * Copyright 2012-2017 the original author or authors.
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

package fm.pattern.microstructure;

import fm.pattern.commons.util.JSON;

public class Reportable {

    private final String code;
    private final String message;

    public static Reportable code(String code) {
        return isProperty(code) ? new Reportable(lookupCode(code), null) : new Reportable(code, null);
    }

    public static Reportable message(String message) {
        return isProperty(message) ? new Reportable(null, lookupMessage(message)) : new Reportable(null, message);
    }

    public static Reportable resolve(String key) {
        return new Reportable(ValidationMessages.getCode(key), ValidationMessages.getMessage(key));
    }

    public Reportable(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private static String lookupCode(String key) {
        return ValidationMessages.getCode(stripBraces(key));
    }

    private static String lookupMessage(String key) {
        return ValidationMessages.getMessage(stripBraces(key));
    }

    private static boolean isProperty(String text) {
        return (text.startsWith("{") && text.endsWith("}"));
    }

    private static String stripBraces(String text) {
        return text.replace("{", "").replace("}", "");
    }

    public String toString() {
        return JSON.stringify(this);
    }

}
