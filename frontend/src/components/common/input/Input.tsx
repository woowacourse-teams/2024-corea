import { InputHTMLAttributes } from "react";
import * as S from "@/components/common/input/Input.style";

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  error?: boolean;
}

export const Input = ({ error = false, ...rest }: InputProps) => {
  return <S.InputContainer $error={error} {...rest} />;
};
