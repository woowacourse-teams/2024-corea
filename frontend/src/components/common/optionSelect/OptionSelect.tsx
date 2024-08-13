import * as S from "./OptionSelect.style";
import { NonEmptyArray } from "@/@types/NonEmptyArray";

interface OptionSelect<T extends NonEmptyArray<string>> {
  selected: T[number];
  options: T;
  handleSelectedOption: (option: T[number]) => void;
}

const OptionSelect = <T extends NonEmptyArray<string>>({
  selected,
  options,
  handleSelectedOption,
}: OptionSelect<T>) => {
  return (
    <S.OptionSelectContainer>
      {options.map((option) => (
        <S.Option
          key={option}
          $isSelected={option === selected}
          onClick={() => handleSelectedOption(option)}
        >
          {option}
        </S.Option>
      ))}
    </S.OptionSelectContainer>
  );
};

export default OptionSelect;
