import React from "react";
import WhatsappIcon from "@/assets/images/whatsapp-icon.png";

const WHATSAPP_NUMBER = "9999999999";
const WHATSAPP_LINK = `https://wa.me/${WHATSAPP_NUMBER}`;

const WhatsappFloat: React.FC = () => (
  <a
    href={WHATSAPP_LINK}
    target="_blank"
    rel="noopener noreferrer"
    aria-label="Chat on WhatsApp"
    className="fixed z-50 bottom-24 right-2 group"
  >
    <div
      className="flex items-center bg-green-500 rounded-full shadow-lg p-4 cursor-pointer transition-all duration-300 ease-in-out w-16 group-hover:w-46 overflow-hidden"
      style={{ boxShadow: "0 4px 16px rgba(100,100,100,0.25)" }}
    >
      <img
        src={WhatsappIcon}
        alt="WhatsApp"
        className="w-8 h-8 object-contain flex-shrink-0"
      />
      <span
        className="text-white font-semibold text-lg whitespace-nowrap opacity-0 group-hover:opacity-100 group-hover:ml-3 transition-all duration-300 ease-in-out"
      > 
        {WHATSAPP_NUMBER}
      </span>
    </div>
  </a>
);

export default WhatsappFloat; 