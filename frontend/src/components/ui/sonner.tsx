import { useTheme } from "next-themes";
import { Toaster as Sonner, ToasterProps } from "sonner";

const Toaster = ({ position = "bottom-right", ...props }: ToasterProps) => {
  const { theme = "system" } = useTheme();
  const isDark = theme === "dark";

  // 🎨 Junior G brand palette
  const colors = {
    primary: "#3D348B",
    secondary: "#7678ED",
    accent: "#F7B801",
    warning: "#F18701",
    danger: "#F35B04",
  };

  return (
    <Sonner
      theme={theme as ToasterProps["theme"]}
      position={position}
      className="toaster group"
      toastOptions={{
        // 🔹 Inline style ensures background is never transparent
        style: {
          background: isDark ? "#1E1E1E" : "#FFFFFF",
          color: isDark ? "#F3F4F6" : "#111827",
          border: `1px solid ${
            isDark ? "rgba(255,255,255,0.08)" : "rgba(0,0,0,0.08)"
          }`,
          boxShadow: "0 6px 18px rgba(15, 23, 42, 0.12)",
          zIndex: 9999,
        },
        // 🔹 Optional Tailwind class styling for structure
        classNames: {
          toast: "rounded-md p-3 font-medium shadow-md",
          title: "font-semibold text-sm",
          description: "text-xs text-gray-600 dark:text-gray-400",
          success: `border-l-4 border-[${colors.primary}]`,
          info: `border-l-4 border-[${colors.secondary}]`,
          warning: `border-l-4 border-[${colors.warning}]`,
          error: `border-l-4 border-[${colors.danger}]`,
        },
      }}
      style={
        {
          "--normal-bg": "var(--popover)",
          "--normal-text": "var(--popover-foreground)",
          "--normal-border": "var(--border)",
        } as React.CSSProperties
      }
      {...props}
    />
  );
};

export { Toaster };
