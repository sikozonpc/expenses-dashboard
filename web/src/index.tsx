import React from 'react';
import 'flowbite';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Layout } from './components/Layout';
import './index.css';
import { App } from './pages/app';
import { RecordsPage } from './pages/records';
import reportWebVitals from './reportWebVitals';
import { Login } from './pages/login/Login';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <Layout>
        <Routes>
          <Route path='/' element={<App />} />
          <Route path='records' element={<RecordsPage />} />
          <Route path='login' element={<Login />} />
          <Route
            path="*"
            element={
              <main style={{ padding: "1rem" }}>
                <p>There's nothing here!</p>
              </main>
            }
          />
        </Routes>
      </Layout>
    </BrowserRouter>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
