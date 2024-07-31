import React from "react";
import { TextareaHTMLAttributes } from "react";
import * as S from "@/components/common/textarea/Textarea.style";

interface TextareaProps extends TextareaHTMLAttributes<HTMLTextAreaElement> {
  error?: boolean;
}

export const Textarea = ({ error = false, ...rest }: TextareaProps) => {
  return <S.Textarea $error={error} {...rest} />;
};
