// src/pages/LoginPage.tsx

import { LoginForm } from "../components/login/login-component";

export default function LoginPage() {
  return (
    <div className="min-h-screen bg-white flex items-start justify-center pt-24 px-4">
      <div className="w-full max-w-[400px]">
        <LoginForm />
      </div>
    </div>
  );
}
