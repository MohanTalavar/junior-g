import { ForgotPasswordForm } from "@/components/login/forgot-password-component";

export default function ForgotPasswordPage() {
  return (
    <div className="min-h-screen bg-white flex items-start justify-center pt-24 px-4">
      <div className="w-full max-w-[400px]">
        <ForgotPasswordForm />
      </div>
    </div>
  );
}
