/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.ode.dao.jpa.hibernate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import org.apache.ode.utils.DbIsolation;
import org.hibernate.HibernateException;
import org.hibernate.service.UnknownUnwrapTypeException;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.spi.Configurable;
import org.hibernate.service.spi.Stoppable;

/**
 * 
 * @author jeffyu
 *
 */
public class DataSourceConnectionProvider implements ConnectionProvider, Configurable, Stoppable  {

  private static final long serialVersionUID = -2686513656521329257L;

  private Properties _props;
  
  private boolean available = true;
  
  public DataSourceConnectionProvider() {
  }
  
  @SuppressWarnings( {"unchecked"})
  public void configure(Map properties) {
	 _props = new Properties();
	 _props.putAll(properties);
  }

  public Connection getConnection() throws SQLException {
	if (!available) {
		throw new HibernateException( "Provider is closed!" );
	}
    Connection c = HibernateUtil.getConnection(_props);
    DbIsolation.setIsolationLevel(c);
    return c;
  }

  public void closeConnection(Connection con) throws SQLException {
    con.close();
  }

  public boolean supportsAggressiveRelease() {
    return true;
  }

  public boolean isUnwrappableAs(Class unwrapType) {
		return ConnectionProvider.class.equals(unwrapType) ||
				DataSourceConnectionProvider.class.isAssignableFrom(unwrapType);
  }
  
  @SuppressWarnings( {"unchecked"})
  public <T> T unwrap(Class<T> unwrapType) {
		if (ConnectionProvider.class.equals(unwrapType) ||
				DataSourceConnectionProvider.class.isAssignableFrom(unwrapType)) {
			return (T) this;
		} else {
			throw new UnknownUnwrapTypeException( unwrapType );
		}
  }

  public void stop() {
	available = false;
  }


}
