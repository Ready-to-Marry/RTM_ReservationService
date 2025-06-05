package ready_to_marry.reservationservice.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ready_to_marry.reservationservice.common.exception.BusinessException;
import ready_to_marry.reservationservice.common.exception.ErrorCode;
import ready_to_marry.reservationservice.common.exception.NotFoundException;
import ready_to_marry.reservationservice.reservation.dto.request.ContractRequest;
import ready_to_marry.reservationservice.reservation.dto.response.ContractListResponse;
import ready_to_marry.reservationservice.reservation.dto.response.ContractResponse;
import ready_to_marry.reservationservice.reservation.entity.Contract;
import ready_to_marry.reservationservice.reservation.entity.Reservation;
import ready_to_marry.reservationservice.reservation.enums.ContractStatus;
import ready_to_marry.reservationservice.reservation.enums.ReservationStatus;
import ready_to_marry.reservationservice.reservation.repository.ContractRepository;
import ready_to_marry.reservationservice.reservation.repository.ReservationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ReservationRepository reservationRepository;
    private final ContractRepository contractRepository;

    @Override
    @Transactional
    public ContractListResponse createContract(ContractRequest request, Long partnerId) {
        Reservation reservation = reservationRepository.findById(request.getReservationId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_RESERVATION));

        if (!reservation.getPartnerId().equals(partnerId)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_RESERVATION_ACCESS);
        }

        if (reservation.getStatus() != ReservationStatus.ANSWERED) {
            throw new BusinessException(ErrorCode.RESERVATION_STATUS_NOT_ANSWERED);
        }

        Contract contract = Contract.builder()
                .userId(reservation.getUserId())
                .partnerId(reservation.getPartnerId())
                .itemId(reservation.getItemId())
                .amount(request.getAmount())
                .contractContent(request.getContractContent())
                .status(ContractStatus.REQUESTED)
                .reservation(reservation)
                .build();

        contractRepository.save(contract);
        return ContractListResponse.from(contract);
    }

    @Override
    @Transactional
    public void markAsCompleted(Long contractId) {
        Contract contract = getValidContract(contractId, ContractStatus.REQUESTED);
        contract.setStatus(ContractStatus.COMPLETED);
    }

    @Override
    @Transactional
    public void markAsRefunded(Long contractId) {
        Contract contract = getValidContract(contractId, ContractStatus.COMPLETED);
        contract.setStatus(ContractStatus.REFUNDED);
    }

    @Override
    @Transactional
    public void cancelByUser(Long contractId, Long userId) {
        Contract contract = getValidContract(contractId, ContractStatus.REQUESTED);
        if (!contract.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_CONTRACT_ACCESS);
        }
        contract.setStatus(ContractStatus.CANCELLED_BY_USER);
    }

    @Override
    @Transactional
    public void cancelByPartner(Long contractId, Long partnerId) {
        Contract contract = getValidContract(contractId, ContractStatus.REQUESTED);
        if (!contract.getPartnerId().equals(partnerId)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_CONTRACT_ACCESS);
        }
        contract.setStatus(ContractStatus.CANCELLED_BY_PARTNER);
    }

    @Override
    public ContractListResponse getContractDetail(Long reservationId, Long partnerId) {
        Contract contract = contractRepository.findByReservation_ReservationId(reservationId);
        if (contract == null || !contract.getPartnerId().equals(partnerId)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_CONTRACT_ACCESS);
        }
        return ContractListResponse.from(contract);
    }

    @Override
    public ContractResponse getContractDetail(Long contractId) {
        Contract contract = contractRepository.findByReservation_ReservationId(contractId);
        ContractResponse result = ContractResponse.builder()
                .partnerId(contract.getPartnerId())
                .itemId(contract.getItemId())
                .amount(contract.getAmount())
                .build();
        return result;
    }

    @Override
    public List<ContractListResponse> getContractsForUser(Long userId) {
        return contractRepository.findByUserId(userId).stream()
                .map(ContractListResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContractListResponse> getContractsForPartner(Long partnerId) {
        return contractRepository.findByPartnerId(partnerId).stream()
                .map(ContractListResponse::from)
                .collect(Collectors.toList());
    }

    private Contract getValidContract(Long id, ContractStatus expectedStatus) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_CONTRACT));
        if (contract.getStatus() != expectedStatus) {
            throw new BusinessException(ErrorCode.INVALID_CONTRACT_STATE);
        }
        return contract;
    }
}
