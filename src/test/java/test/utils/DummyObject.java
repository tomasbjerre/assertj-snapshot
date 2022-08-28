/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2022-2022 the original author or authors.
 */
package test.utils;

public class DummyObject {

  private final String someAttr1;
  private final Integer someAttr2;

  public DummyObject(final String someAttr1, final Integer someAttr2) {
    super();
    this.someAttr1 = someAttr1;
    this.someAttr2 = someAttr2;
  }

  public String getSomeAttr1() {
    return this.someAttr1;
  }

  public Integer getSomeAttr2() {
    return this.someAttr2;
  }
}
