import React from "react";
import { Sidebar } from "./Sidebar"


interface LayoutProps extends React.HTMLAttributes<HTMLDivElement> {

}

export const Layout: React.FC<LayoutProps> = ({ children }) => {

  const isAuth = true;

  return (
    <div className='flex p-2 text-center font-medium text-gray-900 dark:text-white  dark:bg-gray-900'>
      {isAuth && (
        <Sidebar
          className='w-64 h-screen mr-14 text-left'
        />)}

      <main className='w-full h-full'>
        {children}
      </main>
    </div>
  );
}