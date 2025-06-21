import React from "react";
import WhatsappIcon from "@/assets/images/whatsapp-icon.png";
import FacebookIcon from "@/assets/images/facebook-icon.png";
import InstagramIcon from "@/assets/images/instagram-icon.png";

const WHATSAPP_NUMBER = "7719005081";
const FACEBOOK_PROFILE = "profile.php?id=61575842600420";
const INSTAGRAM_PROFILE = "junior_g_preschool?igsh=bjk2Nm5pYzVmOXJz";

const WHATSAPP_LINK = `https://wa.me/${WHATSAPP_NUMBER}`;
const FACEBOOK_LINK = `https://www.facebook.com/${FACEBOOK_PROFILE}`;
const INSTAGRAM_LINK = `https://www.instagram.com/${INSTAGRAM_PROFILE}`;

type SingleFloaterProps = {
  link: string;
  ariaLabel: string;
  positionClass: string;
  bgColorClass: string;
  hoverWidthClass: string;
  iconSrc: string;
  altText: string;
  text: string;
  textColorClass: string;
  extraStyle?: React.CSSProperties;
  padding?: string;
  extraContainerClasses?: string;
};

const SingleFloater: React.FC<SingleFloaterProps> = ({
  link,
  ariaLabel,
  positionClass,
  bgColorClass,
  hoverWidthClass,
  iconSrc,
  altText,
  text,
  textColorClass,
  extraStyle,
  padding,
  extraContainerClasses = "",
}) => (
  <a
    href={link}
    target="_blank"
    rel="noopener noreferrer"
    aria-label={ariaLabel}
    className={`fixed z-50 ${positionClass} right-2 group`}
  >
    <div
      className={`flex items-center ${bgColorClass} rounded-full shadow-lg p-3 cursor-pointer transition-all duration-300 ease-in-out w-12 ${hoverWidthClass} overflow-hidden ${extraContainerClasses}`}
      style={extraStyle}
    >
      <img
        src={iconSrc}
        alt={altText}
        className={`w-6 h-6 ${padding} object-contain flex-shrink-0`}
      />
      <span
        className={`${textColorClass} font-semibold text-base whitespace-nowrap opacity-0 group-hover:opacity-100 ml-0 group-hover:ml-3 transition-all duration-300 ease-in-out`}
      >
        {text}
      </span>
    </div>
  </a>
);

const WhatsappFloat: React.FC = () => (
  <SingleFloater
    link={WHATSAPP_LINK}
    ariaLabel="Chat on WhatsApp"
    positionClass="bottom-12"
    bgColorClass="bg-green-600"
    hoverWidthClass="group-hover:w-36"
    iconSrc={WhatsappIcon}
    altText="WhatsApp"
    text={"WhatsApp"}
    textColorClass="text-white"
    extraStyle={{ boxShadow: "0 4px 16px rgba(100,100,100,0.25)" }}
  />
);

const FacebookFloat: React.FC = () => (
  <SingleFloater
    link={FACEBOOK_LINK}
    ariaLabel="Find us on Facebook"
    positionClass="bottom-44"
    bgColorClass="bg-[#1c74f4]"
    hoverWidthClass="group-hover:w-36"
    iconSrc={FacebookIcon}
    altText="Facebook"
    text="Facebook"
    textColorClass="text-white"
    padding="pb-0.25"
  />
);

const InstagramFloat: React.FC = () => (
  <a
    href={INSTAGRAM_LINK}
    target="_blank"
    rel="noopener noreferrer"
    aria-label="Follow us on Instagram"
    className="fixed z-50 bottom-28 right-2 group"
  >
    <div
      // Outer container for the gradient ring
      className="p-0.5 bg-gradient-to-br from-yellow-400 via-pink-500 to-purple-600 rounded-full shadow-lg transition-all duration-300 ease-in-out w-12 group-hover:w-36"
    >
      <div
        className="flex items-center bg-white rounded-full w-full h-full p-[10px] cursor-pointer overflow-hidden"
      >
        <img
          src={InstagramIcon}
          alt="Instagram"
          className="w-6 h-6 object-contain flex-shrink-0"
        />
        <span
          className="font-semibold text-base whitespace-nowrap opacity-0 group-hover:opacity-100 ml-3 transition-all duration-300 ease-in-out bg-gradient-to-r from-pink-600 to-purple-800 bg-clip-text text-transparent"
        >
          Instagram
        </span>
      </div>
    </div>
  </a>
);

export { WhatsappFloat, InstagramFloat, FacebookFloat };
