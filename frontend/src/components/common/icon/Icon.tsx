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
  MdArrowDropDown,
  MdCalendarMonth,
  MdExpandMore,
  MdInfoOutline,
  MdInsertLink,
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
};

interface IconProps {
  kind: IconKind;
  onClick?: MouseEventHandler<SVGElement>;
  color?: string;
  size?: string | number;
}

const Icon = ({ kind, ...props }: IconProps) => {
  const TargetIcon = ICON[kind];
  return <TargetIcon {...props} width={"2.4rem"} height={"2.4rem"} />;
};

export default Icon;
