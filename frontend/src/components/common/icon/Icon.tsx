import React from "react";
import { MouseEventHandler } from "react";
import { IconType } from "react-icons/lib";
import { MdCalendarMonth, MdInsertLink, MdPerson } from "react-icons/md";
import IconKind from "@/@types/icon";

const ICON: { [key in IconKind]: IconType } = {
  person: MdPerson,
  link: MdInsertLink,
  calendar: MdCalendarMonth,
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
