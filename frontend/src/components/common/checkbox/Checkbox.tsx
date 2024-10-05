import React from "react";
import * as S from "@/components/common/checkbox/Checkbox.style";

interface CheckboxProps {
  id: string;
  label: string;
  checked: boolean;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

const Checkbox = ({ id, label, checked, onChange }: CheckboxProps) => {
  return (
    <S.CheckboxWrapper>
      <S.CheckboxStyle type="checkbox" id={id} name={id} checked={checked} onChange={onChange} />
      <S.StyledLabel htmlFor={id}>{label}</S.StyledLabel>
    </S.CheckboxWrapper>
  );
};

export default Checkbox;
