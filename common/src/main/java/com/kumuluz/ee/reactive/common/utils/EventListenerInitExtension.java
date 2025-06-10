/*
 *  Copyright (c) 2014-2017 Kumuluz and/or its affiliates
 *  and other contributors as indicated by the @author tags and
 *  the contributor list.
 *
 *  Licensed under the MIT License (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  https://opensource.org/licenses/MIT
 *
 *  The software is provided "AS IS", WITHOUT WARRANTY OF ANY KIND, express or
 *  implied, including but not limited to the warranties of merchantability,
 *  fitness for a particular purpose and noninfringement. in no event shall the
 *  authors or copyright holders be liable for any claim, damages or other
 *  liability, whether in an action of contract, tort or otherwise, arising from,
 *  out of or in connection with the software or the use or other dealings in the
 *  software. See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.kumuluz.ee.reactive.common.utils;

import com.kumuluz.ee.reactive.common.annotations.ReactiveEventListener;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessBean;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Extension enables processing of beans with ReactiveEventListener annotation.
 *
 * @author Žan Ožbot
 * @since 1.0.0
 */
public interface EventListenerInitExtension extends Extension {

	List<EventListenerInstance> instanceList = new ArrayList<>();

	default <X> void processVertxEventListeners(@Observes ProcessBean<X> processBean) {

		for (Method method : processBean.getBean().getBeanClass().getMethods()) {
			if (method.getAnnotation(ReactiveEventListener.class) != null) {
				ReactiveEventListener annotation = method.getAnnotation(ReactiveEventListener.class);
				instanceList.add(new EventListenerInstance(processBean.getBean(), method, annotation));
			}
		}
	}

	<X> void after(@Observes AfterDeploymentValidation adv, BeanManager bm);

}
