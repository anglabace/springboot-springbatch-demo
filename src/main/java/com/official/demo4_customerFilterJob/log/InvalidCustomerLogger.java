/*
 * Copyright 2006-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.official.demo4_customerFilterJob.log;

import com.official.demo4_customerFilterJob.domain.CustomerUpdate;

/**
 * Interface for logging invalid customers.  Customers may need to be logged because
 * they already existed when attempted to be added.  Or a non existent customer was
 * updated.
 * 
 * @author Lucas Ward
 *
 */
public interface InvalidCustomerLogger {

	void log(CustomerUpdate customerUpdate);
	
}
