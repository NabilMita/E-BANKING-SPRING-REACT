import React, {useState, useEffect} from 'react';
import { Dashboard } from './Dashboard';
import { LoginPage } from './LoginPage';
import { ClientDashboard } from './ClientDashboard';
import axios from 'axios';

export const Authenticate = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [refresh, setRefresh] = useState(false);
    const [notif, setNotif] = useState({message: '', style: ''});
    const [isAdmin, setIsAdmin] = useState(false);
    const [client, setClient] = useState(null);
    const [data, setData] = useState([]);
    const localUsers = localStorage.getItem('users');
    
    useEffect(() => {
      async function fetchData() {
        if(!refresh){
          localStorage.clear();
          await getData();
          setRefresh(true);
          localStorage.setItem('users', JSON.stringify(data));
        }
      }
      fetchData();
    });
    
     const getData = async () => {
        const options = {
          method: 'GET',
          url: 'http://localhost:8080/compte/all',
        };
        await axios.request(options).then((response) => {
           setData(response.data);
        }).catch((error) => {
          console.error(error);
        });
    };

    if(!localUsers) {
      localStorage.setItem('users', JSON.stringify(data));
    }

    const clients = JSON.parse(localStorage.getItem('users'));

    const isLoginSuccess = (email, password) => {
      let isFound = false;

      clients.forEach(user => {
        if(user.email === email && user.password === password) {
          if(user.role === "ADMIN") {
            setIsAdmin(true);
            setClient(user);
            isFound = true;
          }
          else {
            setIsAdmin(false);
            setClient(user)
            isFound = true;
          }
          setNotif('');
        }
      });
  
      if(!isFound) setNotif({message: 'Wrong username and password.', style: 'danger'});
      return isFound;
    }
  
    const login = (username, password) => {
        if(isLoginSuccess(username, password)) {
            setIsLoggedIn(true);
        }
    }
  
    const logout = () => {
        setRefresh(false);
        setIsLoggedIn(false);
        setIsAdmin(false);
        localStorage.removeItem('users');
        localStorage.removeItem('currentUser');
        localStorage.clear();
        setNotif({message: 'Successfully logged out.', style: 'success'});
    }
  
    if(isLoggedIn) {
      localStorage.setItem('currentUser', JSON.stringify(client));
      if(isAdmin) {
        return <Dashboard users={clients} logoutHandler={logout} />
      } else {
        
        return <ClientDashboard client={client} users={clients} setClient={setClient} logout={logout} />
      }
    } else {
      return <LoginPage loginHandler={login} notif={notif} isLoggedIn={isLoggedIn} />
    }
}
