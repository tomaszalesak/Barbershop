import React, { useState } from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import AdminPage from './admin/AdminPage';
import './App.css';
import LoginPage from './authentication/LoginPage';
import RegisterPage from './authentication/RegisterPage';
import CustomerPage from './customer/CustomerPage';
import EmployeePage from './employee/EmployeePage';
import { store, StoreType } from './store';

const App = (): JSX.Element => {
  const [user, setUser] = useState<StoreType>({
    id: null,
    login: null,
    role: 'UNKNOWN',
    jwt: null,
  });

  return (
    <store.Provider value={{ user, setUser }}>
      <div className="App">
        <BrowserRouter>
          <Switch>
            <Route exact path="/" component={LoginPage} />
            <Route path="/admin" component={AdminPage} />
            <Route path="/customer" component={CustomerPage} />
            <Route path="/employee" component={EmployeePage} />
            <Route exact path="/login" component={LoginPage} />
            <Route exact path="/register" component={RegisterPage} />
            <Route path="*" component={LoginPage} />
          </Switch>
        </BrowserRouter>
      </div>
    </store.Provider>
  );
};

export default App;
