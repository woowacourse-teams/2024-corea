import { InputHTMLAttributes } from "react";
import * as S from "@/components/common/input/Input.style";

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  error?: boolean;
  showCharCount?: boolean;
}

export const Input = ({
  error = false,
  showCharCount = false,
  value = "",
  onChange,
  ...rest
}: InputProps) => {
  const onInputHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (onChange) {
      onChange(e);
    }
  };

  return (
    <S.InputWrapper>
      <S.StyledInput
        $error={error}
        value={value}
        onChange={onInputHandler}
        maxLength={rest.maxLength}
        {...rest}
      />
      {showCharCount && (
        <S.CharCount>
          {value.toString().length}
          {rest.maxLength ? ` / ${rest.maxLength}자` : ""}
        </S.CharCount>
      )}
    </S.InputWrapper>
  );
};
