import { InputHTMLAttributes } from "react";
import useDropdown from "@/hooks/common/useDropdown";
import * as S from "@/components/common/timeDropdown/TimeDropdown.style";
import { Time } from "@/@types/date";

interface TimeDropdownProps extends InputHTMLAttributes<HTMLInputElement> {
  error?: boolean;
  onTimeChange: (time: Time) => void;
  selectedTime: { hour: number; minute: number };
}

interface TimeDropdownChangeProps {
  newTime: Time;
  canCloseDropdown: boolean;
}

const TimePicker = ({
  time,
  onTimeInputChange,
}: {
  time: Time;
  onTimeInputChange: (event: TimeDropdownChangeProps) => void;
}) => {
  return (
    <S.TimePickerWrapper>
      <S.TimeSelector>
        {Array.from({ length: 24 }).map((_, hour) => (
          <S.TimeButton
            key={hour}
            isActive={hour === time.hour}
            onClick={() =>
              onTimeInputChange({ newTime: { ...time, hour }, canCloseDropdown: false })
            }
          >
            {hour < 10 ? `0${hour}` : hour}
          </S.TimeButton>
        ))}
      </S.TimeSelector>

      <S.TimeSelector>
        {Array.from({ length: 60 }).map((_, minute) => (
          <S.TimeButton
            key={minute}
            isActive={minute === time.minute}
            onClick={() =>
              onTimeInputChange({ newTime: { ...time, minute }, canCloseDropdown: true })
            }
          >
            {minute < 10 ? `0${minute}` : minute}
          </S.TimeButton>
        ))}
      </S.TimeSelector>
    </S.TimePickerWrapper>
  );
};

export const TimeDropdown = ({
  error = false,
  onTimeChange,
  selectedTime,
  ...rest
}: TimeDropdownProps) => {
  const { isOpen, handleToggleDropdown, dropdownRef } = useDropdown();

  const handleTimeChange = ({ newTime, canCloseDropdown }: TimeDropdownChangeProps) => {
    onTimeChange(newTime);

    if (canCloseDropdown) handleToggleDropdown();
  };

  return (
    <S.TimeDropdownContainer ref={dropdownRef}>
      <S.TimeDropdownToggle
        type="text"
        value={`${selectedTime.hour < 10 ? `0${selectedTime.hour}` : selectedTime.hour} : ${selectedTime.minute < 10 ? `0${selectedTime.minute}` : selectedTime.minute}`}
        onClick={handleToggleDropdown}
        placeholder="시간을 선택하세요"
        readOnly
        $error={error}
        {...rest}
      />
      {isOpen && <TimePicker time={selectedTime} onTimeInputChange={handleTimeChange} />}
    </S.TimeDropdownContainer>
  );
};
