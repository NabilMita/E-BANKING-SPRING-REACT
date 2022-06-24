import React, { useState } from 'react';
import { Notif } from './Notif';

export const LoginPage = (props) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
  
    const onSubmitHandler = (event) => {
      event.preventDefault();
      props.loginHandler(username, password);
    }
  
    const onChangeUsername = (event) => {
      setUsername(event.target.value);
    }
  
    const onChangePassword = (event) => {
      setPassword(event.target.value);
    }
  
    return (
      <div id="login-page">
        <div id="login">
          <h1 id="logo">Connexion</h1>
          <Notif message={props.notif.message} style={props.notif.style} />
          <form onSubmit={onSubmitHandler}>
            <label htmlFor="username">Nom d'Utilisateur: </label>
            <input id="username" type="text" onChange={onChangeUsername}  value={username} autoComplete="on"  />
            <label htmlFor="password">Mot de passe: </label>
            <input id="password" type="password" onChange={onChangePassword} value={password} autoComplete="on"  />
            <button type="submit" class="btn btn-primary">Se Connecter</button>
          </form>
        </div>
      </div>
    )
}
