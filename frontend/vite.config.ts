import path from "path";
import tailwindcss from "@tailwindcss/vite";
import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import ViteSitemap from "vite-plugin-sitemap";

export default defineConfig({
  plugins: [
    react(),
    tailwindcss(),
    ViteSitemap({
      hostname: "https://juniorg.site", // Your base URL for the sitemap
      dynamicRoutes: [
        "/",          // homepage
        "/summercamp", // specific page
        "/admission-enquiry"
      ],
      exclude: [], // Make sure no routes are excluded incorrectly
    }),
  ],
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src"),
    },
  },
});
