import { InputHTMLAttributes } from "react";
import useDropdown from "@/hooks/common/useDropdown";
import * as S from "@/components/common/timeInput/TimeInput.style";
import { Time } from "@/@types/date";

interface TimeInputProps extends InputHTMLAttributes<HTMLInputElement> {
  error?: boolean;
  onTimeChange: (time: Time) => void;
  selectedTime: { hour: number; minute: number };
}

interface TimeInputChangeProps {
  newTime: Time;
  canCloseDropdown: boolean;
}

const TimePicker = ({
  time,
  onTimeInputChange,
}: {
  time: Time;
  onTimeInputChange: (event: TimeInputChangeProps) => void;
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

export const TimeInput = ({
  error = false,
  onTimeChange,
  selectedTime,
  ...rest
}: TimeInputProps) => {
  const { isOpen, handleToggleDropdown, dropdownRef } = useDropdown();

  const handleTimeChange = ({ newTime, canCloseDropdown }: TimeInputChangeProps) => {
    onTimeChange(newTime);

    if (canCloseDropdown) handleToggleDropdown();
  };

  return (
    <S.TimeInputContainer ref={dropdownRef}>
      <S.TimeInputToggle
        type="text"
        value={`${selectedTime.hour < 10 ? `0${selectedTime.hour}` : selectedTime.hour} : ${selectedTime.minute < 10 ? `0${selectedTime.minute}` : selectedTime.minute}`}
        onClick={handleToggleDropdown}
        placeholder="시간을 선택하세요"
        readOnly
        $error={error}
        {...rest}
      />
      {isOpen && <TimePicker time={selectedTime} onTimeInputChange={handleTimeChange} />}
    </S.TimeInputContainer>
  );
};
