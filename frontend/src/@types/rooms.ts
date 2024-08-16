import { optionsLoggedIn, optionsLoggedOut } from "@/constants/room";

export type OptionLoggedIn = (typeof optionsLoggedIn)[number];
export type OptionLoggedOut = (typeof optionsLoggedOut)[number];
export type Option = OptionLoggedIn | OptionLoggedOut;
