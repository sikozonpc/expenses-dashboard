import { useState } from "react";
import cookie from 'js-cookie';
import { useNavigate } from "react-router-dom";


export const Login: React.FC = () => {
  const [password, setPassword] = useState('');
  const [email, setEmail] = useState('');
  const navigate = useNavigate();

  const handleSubmit = () => {
    void (async () => {
      const data = await fetch(`/auth/login`, {
        method: 'POST',
        body: JSON.stringify({ email, password }),
        headers: {
          'Content-type': 'application/json; charset=UTF-8'
        }
      });

      const { jwt } = await data.json();

      if (jwt) {
        cookie.set('at', jwt);
        return navigate('/', { replace: true });
      }
    })();
  };

  return (
    <div className="max-h-max w-full">
      <div>
        <label htmlFor='email' className='block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300'>Your email</label>
        <input
          name='email'
          value={email}
          onChange={({ target: { value } }) => setEmail(value)}
          id='email'
          className='bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white'
        />
      </div>

      <div>
        <label htmlFor='password' className='block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300'>Your email</label>
        <input
          type='password'
          name='password'
          value={password}
          onChange={({ target: { value } }) => setPassword(value)}
          id='password'
          className='bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white'
        />
      </div>
      <button
        onClick={handleSubmit}
        className='w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800'
      >
        Submit
      </button>
    </div>
  );
}

