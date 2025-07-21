import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

function App() {
   const name = "김우영"
   const position = "사원"
   const email = "ywk5974@gmail.com"
   return(
   // 1. 하나의 부모 요소로 감싸자
    <div className='business-card'>
      <h1>이름: {name}</h1>
      <p>직책: {position}</p>
      <p>email: {email}</p>
      </div>
   )
}

export default App
