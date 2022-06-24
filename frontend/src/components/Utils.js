import axios from "axios";

export function trim(number) {
    return parseFloat(number.replace(/,/g, '')) || 0;
}

export function findAccount(number) {
    const users = JSON.parse(localStorage.getItem('users'));

    for(const user of users) {
        if(user.number == number) {
            return user;
        }
    }

    return false;
}

export async function transaction(op, n, a, ty, t) {
    const options = {
      method: 'POST',
      url: 'http://localhost:8080/transaction/' + op + '/' + n,
   headers: {
        'Content-Type': 'application/json'
    },
    data: {amount: a, type: ty, title: t}
    };
    await axios.request(options).then((response) => {
      console.log(response.data);
    }).catch((error) => {
      console.error(error);
    });
};

export async function transfert_fond(s, r, a, ty, t) {
    const options = {
      method: 'POST',
      url: 'http://localhost:8080/transaction/transfert/' + s + '/' + r,
   headers: {
        'Content-Type': 'application/json'
    },
  data: {amount: a, type: ty, title: t}
    };
    await axios.request(options).then((response) => {
      console.log(response.data);
    }).catch((error) => {
      console.error(error);
    });
};

export function transact(number, amount, type, setUsers=null)
{
    let multiplier = 1;
    if(type === 'add' || type === 'credit') multiplier = 1;
    if(type === 'subtract' || type === 'debit') multiplier = -1;

    const users = JSON.parse(localStorage.getItem('users'));

    for(const user of users) {
        if(user.number == number) {
            user.balance += amount * multiplier;

            if(type === 'add' || type === 'credit') {
                user.transactions.unshift({
                    title: `Deposit`, 
                    amount: amount, 
                    type: "credit", 
                    date: getDateToday()
                })
                transaction("virement",user.number,amount,"credit","Deposit");
            }

            if(type === 'subtract' || type === 'debit') {
                user.transactions.unshift({
                    title: `Withdraw`, 
                    amount: amount, 
                    type: "debit", 
                    date: getDateToday()
                })
                transaction("retrait",user.number,amount,"debit","Withdraw");
            }
        }
    }
    setUsers(users);
    localStorage.setItem('users', JSON.stringify(users));
}

export function capitalize(str) 
{
    return str.charAt(0).toUpperCase() + str.slice(1);
}

export function saveBudgetToDB(accountNumber, newBudget) 
{   
    const user = findAccount(accountNumber);
    user.budget = newBudget;
    const filteredUsers = addUserToUsers(user);
    localStorage.setItem('users', JSON.stringify(filteredUsers));
}

function addUserToUsers(user) {
    const users = JSON.parse(localStorage.getItem('users'));

    const filteredUsers = users.filter(dbUser => {
        return dbUser.number != user.number;
    });

    filteredUsers.push(user);
    return filteredUsers;
}

export function getDateToday() {
    const transDate = new Date();
    const dd = String(transDate.getDate()).padStart(2, '0');
    const mm = String(transDate.getMonth() + 1).padStart(2, '0'); 
    const yyyy = transDate.getFullYear();

    const d = yyyy + '/' + mm + '/' + dd;
    return `${d}`;
}

export async function update_Account(n, f, b) {
    const options = {
      method: 'POST',
      url: 'http://localhost:8080/compte/edit/' + n,
      headers: {
        'Content-Type': 'application/json'
      },
      data: {fullname: f, balance: b}
    };
    await axios.request(options).then((response) => {
      console.log(response.data);
    }).catch((error) => {
      console.error(error);
    });
};


export async function delete_Account(n) {
    const options = {
      method: 'DELETE',
      url: 'http://localhost:8080/compte/delete/' + n
    };
    await axios.request(options).then((response) => {
      console.log(response.data);
    }).catch((error) => {
      console.error(error);
    });
};