// src/components/Layout.jsx
import { useAuth } from '../contexts/AuthContext';
import Header from './Header';
import AdminHeader from './AdminHeader';

export default function Layout({ children }) {
  const { user } = useAuth();
  const isAdmin = user && user.role === 'admin';

  return (
    <>
      {isAdmin ? <AdminHeader /> : <Header />}
      {children}
    </>
  );
}
