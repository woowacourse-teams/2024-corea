import { MouseEventHandler } from "react";
import {
  FaRegFaceFrown,
  FaRegFaceGrinHearts,
  FaRegFaceGrinWide,
  FaRegFaceMeh,
  FaRegFaceSmile,
} from "react-icons/fa6";
import { IconType } from "react-icons/lib";
import {
  MdArrowBackIos,
  MdArrowDropDown,
  MdArrowForwardIos,
  MdAutorenew,
  MdCalendarMonth,
  MdCheck,
  MdClear,
  MdExpandMore,
  MdInfoOutline,
  MdInsertLink,
  MdMenu,
  MdOutlineArrowDropDown,
  MdOutlineArrowDropUp,
  MdOutlineCreate,
  MdOutlinePeopleAlt,
  MdOutlineStar,
  MdOutlineThumbDown,
  MdOutlineThumbUp,
  MdPerson,
} from "react-icons/md";
import IconKind from "@/@types/icon";

const ICON: { [key in IconKind]: IconType } = {
  person: MdPerson,
  link: MdInsertLink,
  calendar: MdCalendarMonth,
  plus: MdExpandMore,
  info: MdInfoOutline,
  star: MdOutlineStar,
  people: MdOutlinePeopleAlt,
  pencil: MdOutlineCreate,
  arrowDown: MdArrowDropDown,
  thumbDown: MdOutlineThumbDown,
  thumbUp: MdOutlineThumbUp,
  bad: FaRegFaceFrown,
  disappointing: FaRegFaceMeh,
  average: FaRegFaceSmile,
  satisfied: FaRegFaceGrinWide,
  verySatisfied: FaRegFaceGrinHearts,
  arrowLeft: MdArrowBackIos,
  arrowRight: MdArrowForwardIos,
  arrowDropDown: MdOutlineArrowDropDown,
  arrowDropUp: MdOutlineArrowDropUp,
  arrowRenew: MdAutorenew,
  check: MdCheck,
  menu: MdMenu,
  close: MdClear,
};

interface IconProps {
  kind: IconKind;
  onClick?: MouseEventHandler<SVGElement>;
  color?: string;
  size?: string | number;
}

const Icon = ({ kind, size = "1.6rem", ...props }: IconProps) => {
  const TargetIcon = ICON[kind];
  return <TargetIcon {...props} size={size} />;
};

export default Icon;
