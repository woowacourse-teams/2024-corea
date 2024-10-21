import * as S from "@/components/common/optionSelect/OptionSelect.style";
import { NonEmptyArray } from "@/@types/NonEmptyArray";

interface OptionSelectProps<T extends NonEmptyArray<string>> {
  selected: T[number];
  options: T;
  handleSelectedOption: (option: T[number]) => void;
}

const OptionSelect = <T extends NonEmptyArray<string>>({
  selected,
  options,
  handleSelectedOption,
}: OptionSelectProps<T>) => {
  const selectedIndex = options.indexOf(selected);

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
      <S.Indicator $position={selectedIndex} $optionCount={options.length} />
    </S.OptionSelectContainer>
  );
};

export default OptionSelect;
